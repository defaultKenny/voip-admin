import { element, by, ElementFinder } from 'protractor';

export class SipAccountComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-sip-account div table .btn-danger'));
  title = element.all(by.css('jhi-sip-account div h2#page-heading span')).first();

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

export class SipAccountUpdatePage {
  pageTitle = element(by.id('jhi-sip-account-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  usernameInput = element(by.id('field_username'));
  passwordInput = element(by.id('field_password'));
  lineEnabledInput = element(by.id('field_lineEnabled'));
  lineNumberInput = element(by.id('field_lineNumber'));
  isManuallyCreatedInput = element(by.id('field_isManuallyCreated'));
  pbxAccountSelect = element(by.id('field_pbxAccount'));
  deviceSelect = element(by.id('field_device'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setUsernameInput(username) {
    await this.usernameInput.sendKeys(username);
  }

  async getUsernameInput() {
    return await this.usernameInput.getAttribute('value');
  }

  async setPasswordInput(password) {
    await this.passwordInput.sendKeys(password);
  }

  async getPasswordInput() {
    return await this.passwordInput.getAttribute('value');
  }

  getLineEnabledInput() {
    return this.lineEnabledInput;
  }
  async setLineNumberInput(lineNumber) {
    await this.lineNumberInput.sendKeys(lineNumber);
  }

  async getLineNumberInput() {
    return await this.lineNumberInput.getAttribute('value');
  }

  getIsManuallyCreatedInput() {
    return this.isManuallyCreatedInput;
  }

  async pbxAccountSelectLastOption() {
    await this.pbxAccountSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async pbxAccountSelectOption(option) {
    await this.pbxAccountSelect.sendKeys(option);
  }

  getPbxAccountSelect(): ElementFinder {
    return this.pbxAccountSelect;
  }

  async getPbxAccountSelectedOption() {
    return await this.pbxAccountSelect.element(by.css('option:checked')).getText();
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

export class SipAccountDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-sipAccount-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-sipAccount'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
