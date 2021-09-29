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

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayModal, {ClayModalProvider, useModal} from '@clayui/modal';
import React, {useEffect, useState} from 'react';

import CustomSelect from './form/CustomSelect';
import Input from './form/Input';

interface IProps extends React.HTMLAttributes<HTMLElement> {
	apiURL: string;
	objectActionExecutors: TObjectActionExecutor[];
	objectActionTriggers: TObjectActionTrigger[];
}

type TObjectActionTrigger = {
	description: string;
	key: string;
	label: string;
};

type TObjectActionExecutor = {
	description: string;
	key: string;
	label: string;
};

type TFormState = {
	name: string;
	objectActionExecutor: {
		key: string;
	};
	objectActionTrigger: {
		key: string;
	};
};

const headers = new Headers({
	Accept: 'application/json',
	'Content-Type': 'application/json',
});

const ModalAddObjectAction: React.FC<IProps> = ({
	apiURL,
	objectActionExecutors,
	objectActionTriggers,
}) => {
	const [visibleModal, setVisibleModal] = useState<boolean>(false);
	const [formState, setFormState] = useState<TFormState>({
		name: '',
		objectActionExecutor: {key: ''},
		objectActionTrigger: {key: ''},
	});
	const [error, setError] = useState<string>('');

	const {observer, onClose} = useModal({
		onClose: () => setVisibleModal(false),
	});

	const handleSaveObjectAction = async () => {
		const {name, objectActionExecutor, objectActionTrigger} = formState;

		const response = await Liferay.Util.fetch(apiURL, {
			body: JSON.stringify({
				active: false,
				name,
				objectActionExecutorKey: objectActionExecutor.key,
				objectActionTriggerKey: objectActionTrigger.key,
			}),
			headers,
			method: 'POST',
		});

		if (response.status === 401) {
			window.location.reload();
		}
		else if (response.ok) {
			onClose();

			window.location.reload();
		}
		else {
			const {
				title = Liferay.Language.get('an-error-occurred'),
			} = await response.json();

			setError(title);
		}
	};

	const handleOpenObjectActionModal = () => setVisibleModal(true);

	useEffect(() => {
		Liferay.on('addObjectAction', handleOpenObjectActionModal);

		return () => {
			Liferay.detach('addObjectAction', handleOpenObjectActionModal);
		};
	}, []);

	return (
		<>
			{visibleModal && (
				<ClayModal observer={observer}>
					<ClayModal.Header>
						{Liferay.Language.get('new-action')}
					</ClayModal.Header>

					<ClayModal.Body>
						{error && (
							<ClayAlert displayType="danger">{error}</ClayAlert>
						)}

						<Input
							id="objectActionName"
							label={Liferay.Language.get('action-name')}
							name="objectActionName"
							onChange={({target: {value}}: any) => {
								setFormState({
									...formState,
									name: value,
								});

								error && setError('');
							}}
							required
							value={formState.name}
						/>

						<CustomSelect
							label={Liferay.Language.get('when')}
							onChange={(objectActionTrigger: any) => {
								setFormState({
									...formState,
									objectActionTrigger,
								});

								error && setError('');
							}}
							options={objectActionTriggers}
							required
							value={formState.objectActionTrigger.key}
						>
							{({description, label}) => (
								<>
									<div>{label}</div>
									<span className="text-small">{description}</span>
								</>
							)}
						</CustomSelect>

						<CustomSelect
							label={Liferay.Language.get('then')}
							onChange={(objectActionExecutor: any) => {
								setFormState({
									...formState,
									objectActionExecutor,
								});

								error && setError('');
							}}
							options={objectActionExecutors}
							required
							value={formState.objectActionExecutor.key}
						>
							{({description, label}) => (
								<>
									<div>{label}</div>
									<span className="text-small">{description}</span>
								</>
							)}
						</CustomSelect>
					</ClayModal.Body>

					<ClayModal.Footer
						last={
							<ClayButton.Group key={1} spaced>
								<ClayButton
									displayType="secondary"
									onClick={() => onClose()}
								>
									{Liferay.Language.get('cancel')}
								</ClayButton>

								<ClayButton
									displayType="primary"
									onClick={() => handleSaveObjectAction()}
								>
									{Liferay.Language.get('save')}
								</ClayButton>
							</ClayButton.Group>
						}
					/>
				</ClayModal>
			)}
		</>
	);
};

const ModalWithProvider: React.FC<IProps> = ({
	apiURL,
	objectActionExecutors,
	objectActionTriggers,
}) => {
	return (
		<ClayModalProvider>
			<ModalAddObjectAction
				apiURL={apiURL}
				objectActionExecutors={objectActionExecutors}
				objectActionTriggers={objectActionTriggers}
			/>
		</ClayModalProvider>
	);
};

export default ModalWithProvider;
