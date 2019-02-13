/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.arquillian.extension.junit.bridge.remote.manager;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.Callable;

import org.jboss.arquillian.core.api.Injector;
import org.jboss.arquillian.core.api.annotation.ApplicationScoped;
import org.jboss.arquillian.core.api.event.ManagerStarted;
import org.jboss.arquillian.core.api.event.ManagerStopping;
import org.jboss.arquillian.core.impl.EventImpl;
import org.jboss.arquillian.core.impl.ExtensionImpl;
import org.jboss.arquillian.core.impl.InjectorImpl;
import org.jboss.arquillian.core.impl.context.ApplicationContextImpl;
import org.jboss.arquillian.core.spi.EventContext;
import org.jboss.arquillian.core.spi.EventPoint;
import org.jboss.arquillian.core.spi.Extension;
import org.jboss.arquillian.core.spi.InjectionPoint;
import org.jboss.arquillian.core.spi.InvocationException;
import org.jboss.arquillian.core.spi.Manager;
import org.jboss.arquillian.core.spi.NonManagedObserver;
import org.jboss.arquillian.core.spi.ObserverMethod;
import org.jboss.arquillian.core.spi.context.ApplicationContext;
import org.jboss.arquillian.core.spi.context.Context;
import org.jboss.arquillian.core.spi.context.ObjectStore;
import org.jboss.arquillian.core.spi.event.ManagerProcessing;

/**
 * @author Matthew Tambara
 */
public class ManagerImpl implements Manager {

	public ManagerImpl(
		Collection<Class<? extends Context>> contextClasses,
		Collection<Class<?>> extensionClasses) {

		try {
			List<Extension> createdExtensions = _createExtensions(
				extensionClasses);

			List<Context> createdContexts = _createContexts(contextClasses);

			_createBuiltInServices();

			_contexts.addAll(createdContexts);

			_extensions.addAll(createdExtensions);

			_addContextsToApplicationScope();

			fireProcessing();

			_addContextsToApplicationScope();
		}
		catch (Exception e) {
			throw new RuntimeException(
				"Could not create and process manager", e);
		}
	}

	@Override
	public <T> void bind(
		Class<? extends Annotation> scope, Class<T> type, T instance) {

		Context scopedContext = _getScopedContext(scope);

		if (scopedContext == null) {
			throw new IllegalArgumentException(
				"No Context registered with support for scope: " + scope);
		}

		if (!scopedContext.isActive()) {
			throw new IllegalArgumentException(
				"No active " + scope.getSimpleName() + " Context to bind to");
		}

		ObjectStore objectStore = scopedContext.getObjectStore();

		objectStore.add(type, instance);
	}

	public <T> void bindAndFire(
		Class<? extends Annotation> scope, Class<T> type, T instance) {

		bind(scope, type, instance);

		fire(instance);
	}

	public <T> T executeInApplicationContext(Callable<T> callable)
		throws Exception {

		ApplicationContext context = (ApplicationContext)_getScopedContext(
			ApplicationScoped.class);

		boolean activatedByUs = false;

		try {
			if (!context.isActive()) {
				context.activate();

				activatedByUs = true;
			}

			return callable.call();
		}
		finally {
			if (activatedByUs && context.isActive()) {
				context.deactivate();
			}
		}
	}

	@Override
	public void fire(Object event) {
		fire(event, null);
	}

	@Override
	public <T> void fire(T event, NonManagedObserver<T> nonManagedObserver) {
		List<ObserverMethod> observers = _resolveObservers(event.getClass());

		List<ObserverMethod> interceptorObservers =
			_resolveInterceptorObservers(event.getClass());

		ApplicationContext context = (ApplicationContext)_getScopedContext(
			ApplicationScoped.class);

		boolean activatedApplicationContext = false;

		try {
			if (!context.isActive()) {
				context.activate();

				activatedApplicationContext = true;
			}

			EventContext<T> eventContext = new EventContextImpl<>(
				this, interceptorObservers, observers, nonManagedObserver,
				event);

			eventContext.proceed();
		}
		catch (InvocationException ie) {
			_throwException(ie.getCause());
		}
		finally {
			if (activatedApplicationContext && context.isActive()) {
				context.deactivate();
			}
		}
	}

	public void fireProcessing() {
		Set<Class<?>> extensions = new HashSet<>();

		Set<Class<? extends Context>> contexts = new HashSet<>();

		fire(
			new ManagerProcessing() {

				@Override
				public ManagerProcessing context(
					Class<? extends Context> context) {

					contexts.add(context);

					return this;
				}

				@Override
				public ManagerProcessing observer(Class<?> observer) {
					extensions.add(observer);

					return this;
				}

			});

		_extensions.addAll(_createExtensions(extensions));
		_contexts.addAll(_createContexts(contexts));
	}

	@Override
	public <T> T getContext(Class<T> type) {
		for (Context context : _contexts) {
			if (type.isInstance(context)) {
				return type.cast(context);
			}
		}

		return null;
	}

	public List<Context> getContexts() {
		return Collections.unmodifiableList(_contexts);
	}

	public <T> T getExtension(Class<T> type) {
		for (Extension extension : _extensions) {
			Object target = ((ExtensionImpl)extension).getTarget();

			if (type.isInstance(target)) {
				return type.cast(target);
			}
		}

		return null;
	}

	@Override
	public void inject(Object obj) {
		_inject(ExtensionImpl.of(obj));
	}

	@Override
	public <T> T resolve(Class<T> type) {
		List<Context> activeContexts = _resolveActiveContexts();

		for (int i = activeContexts.size() - 1; i >= 0; i--) {
			Context context = activeContexts.get(i);

			ObjectStore objectStore = context.getObjectStore();

			T object = objectStore.get(type);

			if (object != null) {
				return object;
			}
		}

		return null;
	}

	@Override
	public void shutdown() {
		fire(new ManagerStopping());

		synchronized (this) {
			for (Context context : _contexts) {
				context.clearAll();
			}

			_contexts.clear();

			_extensions.clear();

			if (_eventStack != null) {
				_eventStack.remove();
			}
		}
	}

	@Override
	public void start() {
		fire(new ManagerStarted());

		getContext(ApplicationContext.class).activate();
	}

	private static <T, E extends Throwable> T _throwException(
			Throwable throwable)
		throws E {

		throw (E)throwable;
	}

	private void _addContextsToApplicationScope() throws Exception {
		executeInApplicationContext(
			new Callable<Void>() {

				@Override
				public Void call() throws Exception {
					ApplicationContext applicationContext = getContext(
						ApplicationContext.class);

					ObjectStore store = applicationContext.getObjectStore();

					for (Context context : _contexts) {
						Class<?> clazz = context.getClass();

						Class<?>[] interfaces = clazz.getInterfaces();

						store.add((Class<Context>)interfaces[0], context);
					}

					return null;
				}

			});
	}

	private void _createBuiltInServices() throws Exception {
		ApplicationContext applicationContext = new ApplicationContextImpl();

		_contexts.add(applicationContext);

		executeInApplicationContext(
			new Callable<Object>() {

				@Override
				public Object call() throws Exception {
					bind(
						ApplicationScoped.class, Injector.class,
						InjectorImpl.of(ManagerImpl.this));

					return null;
				}

			});
	}

	private List<Context> _createContexts(
		Collection<Class<? extends Context>> contextClasses) {

		List<Context> created = new ArrayList<>();

		for (Class<? extends Context> contextClass : contextClasses) {
			created.add(_createInstance(contextClass));
		}

		return created;
	}

	private List<Extension> _createExtensions(
		Collection<Class<?>> extensionClasses) {

		List<Extension> created = new ArrayList<>();

		for (Class<?> extensionClass : extensionClasses) {
			Extension extension = ExtensionImpl.of(
				_createInstance(extensionClass));

			_inject(extension);

			created.add(extension);
		}

		return created;
	}

	private <T>T _createInstance(Class<T> clazz) {
		try {
			Constructor<T> constructor = clazz.getDeclaredConstructor();

			constructor.setAccessible(true);

			return constructor.newInstance();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private Context _getScopedContext(Class<? extends Annotation> scope) {
		for (Context context : _contexts) {
			if (context.getScope() == scope) {
				return context;
			}
		}

		return null;
	}

	private Class<?> _getType(Type type) {
		if (type instanceof Class<?>) {
			return (Class<?>)type;
		}
		else if (type instanceof ParameterizedType) {
			return _getType(
				((ParameterizedType)type).getActualTypeArguments()[0]);
		}

		return null;
	}

	private void _inject(Extension extension) {
		_injectInstances(extension);

		_injectEvents(extension);
	}

	private void _injectEvents(Extension extension) {
		for (EventPoint eventPoint : extension.getEventPoints()) {
			eventPoint.set(EventImpl.of(_getType(eventPoint.getType()), this));
		}
	}

	private void _injectInstances(Extension extension) {
		for (InjectionPoint injectionPoint : extension.getInjectionPoints()) {
			injectionPoint.set(
				InstanceImpl.of(
					_getType(injectionPoint.getType()),
					injectionPoint.getScope(), this));
		}
	}

	private boolean _isType(Type type, Class<?> clazz) {
		if (type instanceof Class<?> && ((Class<?>)type == clazz)) {
			return true;
		}
		else if (type instanceof ParameterizedType &&
				 (((ParameterizedType)type).getRawType() == clazz)) {

			return true;
		}

		return false;
	}

	private List<Context> _resolveActiveContexts() {
		List<Context> activeContexts = new ArrayList<>();

		for (Context context : _contexts) {
			if (context.isActive()) {
				activeContexts.add(context);
			}
		}

		return activeContexts;
	}

	private List<ObserverMethod> _resolveInterceptorObservers(
		Class<?> eventClass) {

		List<ObserverMethod> observers = new ArrayList<>();

		for (Extension extension : _extensions) {
			for (ObserverMethod observer : extension.getObservers()) {
				Type type = observer.getType();

				Class<?> clazz = _getType(type);

				if (clazz.isAssignableFrom(eventClass) &&
					_isType(type, EventContext.class)) {

					observers.add(observer);
				}
			}
		}

		Collections.sort(observers);

		return observers;
	}

	private List<ObserverMethod> _resolveObservers(Class<?> eventClass) {
		List<ObserverMethod> observers = new ArrayList<>();

		for (Extension extension : _extensions) {
			for (ObserverMethod observer : extension.getObservers()) {
				Type type = observer.getType();

				Class<?> clazz = _getType(type);

				if (clazz.isAssignableFrom(eventClass) &&
					!_isType(type, EventContext.class)) {

					observers.add(observer);
				}
			}
		}

		Collections.sort(observers);

		return observers;
	}

	private final List<Context> _contexts = new ArrayList<>();
	private ThreadLocal<Stack<Object>> _eventStack;
	private final List<Extension> _extensions = new ArrayList<>();

}