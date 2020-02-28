import { element, by, ElementFinder } from 'protractor';

export class PbxAccountComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-pbx-account div table .btn-danger'));
  title = element.all(by.css('jhi-pbx-account div h2#page-heading span')).first();

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

export class PbxAccountUpdatePage {
  pageTitle = element(by.id('jhi-pbx-account-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  usernameInput = element(by.id('field_username'));
  pbxIdInput = element(by.id('field_pbxId'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setUsernameInput(username) {
    await this.usernameInput.sendKeys(username);
  }

  async getUsernameInput() {
    return await this.usernameInput.getAttribute('value');
  }

  async setPbxIdInput(pbxId) {
    await this.pbxIdInput.sendKeys(pbxId);
  }

  async getPbxIdInput() {
    return await this.pbxIdInput.getAttribute('value');
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

export class PbxAccountDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-pbxAccount-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-pbxAccount'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
