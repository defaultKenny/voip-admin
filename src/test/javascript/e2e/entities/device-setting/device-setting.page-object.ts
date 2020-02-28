import { element, by, ElementFinder } from 'protractor';

export class DeviceSettingComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-device-setting div table .btn-danger'));
  title = element.all(by.css('jhi-device-setting div h2#page-heading span')).first();

  async clickOnCreateButton() {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton() {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons() {
    return this.deleteButtons.count();
  }

  async getTitle() {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class DeviceSettingUpdatePage {
  pageTitle = element(by.id('jhi-device-setting-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  valueInput = element(by.id('field_value'));
  deviceSelect = element(by.id('field_device'));
  additionalOptionSelect = element(by.id('field_additionalOption'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setValueInput(value) {
    await this.valueInput.sendKeys(value);
  }

  async getValueInput() {
    return await this.valueInput.getAttribute('value');
  }

  async deviceSelectLastOption() {
    await this.deviceSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async deviceSelectOption(option) {
    await this.deviceSelect.sendKeys(option);
  }

  getDeviceSelect(): ElementFinder {
    return this.deviceSelect;
  }

  async getDeviceSelectedOption() {
    return await this.deviceSelect.element(by.css('option:checked')).getText();
  }

  async additionalOptionSelectLastOption() {
    await this.additionalOptionSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async additionalOptionSelectOption(option) {
    await this.additionalOptionSelect.sendKeys(option);
  }

  getAdditionalOptionSelect(): ElementFinder {
    return this.additionalOptionSelect;
  }

  async getAdditionalOptionSelectedOption() {
    return await this.additionalOptionSelect.element(by.css('option:checked')).getText();
  }

  async save() {
    await this.saveButton.click();
  }

  async cancel() {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class DeviceSettingDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-deviceSetting-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-deviceSetting'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
