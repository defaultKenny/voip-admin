import { element, by, ElementFinder } from 'protractor';

export class DeviceModelComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-device-model div table .btn-danger'));
  title = element.all(by.css('jhi-device-model div h2#page-heading span')).first();

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

export class DeviceModelUpdatePage {
  pageTitle = element(by.id('jhi-device-model-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  nameInput = element(by.id('field_name'));
  isConfigurableInput = element(by.id('field_isConfigurable'));
  linesCountInput = element(by.id('field_linesCount'));
  configFileInput = element(by.id('file_configFile'));
  firmwareFileInput = element(by.id('file_firmwareFile'));
  additionalOptionsSelect = element(by.id('field_additionalOptions'));
  deviceTypeSelect = element(by.id('field_deviceType'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNameInput(name) {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput() {
    return await this.nameInput.getAttribute('value');
  }

  getIsConfigurableInput() {
    return this.isConfigurableInput;
  }
  async setLinesCountInput(linesCount) {
    await this.linesCountInput.sendKeys(linesCount);
  }

  async getLinesCountInput() {
    return await this.linesCountInput.getAttribute('value');
  }

  async setConfigFileInput(configFile) {
    await this.configFileInput.sendKeys(configFile);
  }

  async getConfigFileInput() {
    return await this.configFileInput.getAttribute('value');
  }

  async setFirmwareFileInput(firmwareFile) {
    await this.firmwareFileInput.sendKeys(firmwareFile);
  }

  async getFirmwareFileInput() {
    return await this.firmwareFileInput.getAttribute('value');
  }

  async additionalOptionsSelectLastOption() {
    await this.additionalOptionsSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async additionalOptionsSelectOption(option) {
    await this.additionalOptionsSelect.sendKeys(option);
  }

  getAdditionalOptionsSelect(): ElementFinder {
    return this.additionalOptionsSelect;
  }

  async getAdditionalOptionsSelectedOption() {
    return await this.additionalOptionsSelect.element(by.css('option:checked')).getText();
  }

  async deviceTypeSelectLastOption() {
    await this.deviceTypeSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async deviceTypeSelectOption(option) {
    await this.deviceTypeSelect.sendKeys(option);
  }

  getDeviceTypeSelect(): ElementFinder {
    return this.deviceTypeSelect;
  }

  async getDeviceTypeSelectedOption() {
    return await this.deviceTypeSelect.element(by.css('option:checked')).getText();
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

export class DeviceModelDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-deviceModel-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-deviceModel'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
