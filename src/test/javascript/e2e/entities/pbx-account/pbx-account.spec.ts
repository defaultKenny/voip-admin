import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { PbxAccountComponentsPage, PbxAccountDeleteDialog, PbxAccountUpdatePage } from './pbx-account.page-object';

const expect = chai.expect;

describe('PbxAccount e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let pbxAccountComponentsPage: PbxAccountComponentsPage;
  let pbxAccountUpdatePage: PbxAccountUpdatePage;
  let pbxAccountDeleteDialog: PbxAccountDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load PbxAccounts', async () => {
    await navBarPage.goToEntity('pbx-account');
    pbxAccountComponentsPage = new PbxAccountComponentsPage();
    await browser.wait(ec.visibilityOf(pbxAccountComponentsPage.title), 5000);
    expect(await pbxAccountComponentsPage.getTitle()).to.eq('voipAdminApp.pbxAccount.home.title');
  });

  it('should load create PbxAccount page', async () => {
    await pbxAccountComponentsPage.clickOnCreateButton();
    pbxAccountUpdatePage = new PbxAccountUpdatePage();
    expect(await pbxAccountUpdatePage.getPageTitle()).to.eq('voipAdminApp.pbxAccount.home.createOrEditLabel');
    await pbxAccountUpdatePage.cancel();
  });

  it('should create and save PbxAccounts', async () => {
    const nbButtonsBeforeCreate = await pbxAccountComponentsPage.countDeleteButtons();

    await pbxAccountComponentsPage.clickOnCreateButton();
    await promise.all([pbxAccountUpdatePage.setUsernameInput('username'), pbxAccountUpdatePage.setPbxIdInput('pbxId')]);
    expect(await pbxAccountUpdatePage.getUsernameInput()).to.eq('username', 'Expected Username value to be equals to username');
    expect(await pbxAccountUpdatePage.getPbxIdInput()).to.eq('pbxId', 'Expected PbxId value to be equals to pbxId');
    await pbxAccountUpdatePage.save();
    expect(await pbxAccountUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await pbxAccountComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last PbxAccount', async () => {
    const nbButtonsBeforeDelete = await pbxAccountComponentsPage.countDeleteButtons();
    await pbxAccountComponentsPage.clickOnLastDeleteButton();

    pbxAccountDeleteDialog = new PbxAccountDeleteDialog();
    expect(await pbxAccountDeleteDialog.getDialogTitle()).to.eq('voipAdminApp.pbxAccount.delete.question');
    await pbxAccountDeleteDialog.clickOnConfirmButton();

    expect(await pbxAccountComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
