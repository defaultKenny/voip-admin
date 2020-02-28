import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { AdditionalOptionComponentsPage, AdditionalOptionDeleteDialog, AdditionalOptionUpdatePage } from './additional-option.page-object';

const expect = chai.expect;

describe('AdditionalOption e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let additionalOptionComponentsPage: AdditionalOptionComponentsPage;
  let additionalOptionUpdatePage: AdditionalOptionUpdatePage;
  let additionalOptionDeleteDialog: AdditionalOptionDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load AdditionalOptions', async () => {
    await navBarPage.goToEntity('additional-option');
    additionalOptionComponentsPage = new AdditionalOptionComponentsPage();
    await browser.wait(ec.visibilityOf(additionalOptionComponentsPage.title), 5000);
    expect(await additionalOptionComponentsPage.getTitle()).to.eq('voipAdminApp.additionalOption.home.title');
  });

  it('should load create AdditionalOption page', async () => {
    await additionalOptionComponentsPage.clickOnCreateButton();
    additionalOptionUpdatePage = new AdditionalOptionUpdatePage();
    expect(await additionalOptionUpdatePage.getPageTitle()).to.eq('voipAdminApp.additionalOption.home.createOrEditLabel');
    await additionalOptionUpdatePage.cancel();
  });

  it('should create and save AdditionalOptions', async () => {
    const nbButtonsBeforeCreate = await additionalOptionComponentsPage.countDeleteButtons();

    await additionalOptionComponentsPage.clickOnCreateButton();
    await promise.all([additionalOptionUpdatePage.setCodeInput('code'), additionalOptionUpdatePage.setDescriptionInput('description')]);
    expect(await additionalOptionUpdatePage.getCodeInput()).to.eq('code', 'Expected Code value to be equals to code');
    expect(await additionalOptionUpdatePage.getDescriptionInput()).to.eq(
      'description',
      'Expected Description value to be equals to description'
    );
    await additionalOptionUpdatePage.save();
    expect(await additionalOptionUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await additionalOptionComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last AdditionalOption', async () => {
    const nbButtonsBeforeDelete = await additionalOptionComponentsPage.countDeleteButtons();
    await additionalOptionComponentsPage.clickOnLastDeleteButton();

    additionalOptionDeleteDialog = new AdditionalOptionDeleteDialog();
    expect(await additionalOptionDeleteDialog.getDialogTitle()).to.eq('voipAdminApp.additionalOption.delete.question');
    await additionalOptionDeleteDialog.clickOnConfirmButton();

    expect(await additionalOptionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
