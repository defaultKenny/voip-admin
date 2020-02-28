import { browser, ExpectedConditions as ec /* , promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  SipAccountComponentsPage,
  /* SipAccountDeleteDialog,
   */ SipAccountUpdatePage
} from './sip-account.page-object';

const expect = chai.expect;

describe('SipAccount e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let sipAccountComponentsPage: SipAccountComponentsPage;
  let sipAccountUpdatePage: SipAccountUpdatePage;
  /* let sipAccountDeleteDialog: SipAccountDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load SipAccounts', async () => {
    await navBarPage.goToEntity('sip-account');
    sipAccountComponentsPage = new SipAccountComponentsPage();
    await browser.wait(ec.visibilityOf(sipAccountComponentsPage.title), 5000);
    expect(await sipAccountComponentsPage.getTitle()).to.eq('voipAdminApp.sipAccount.home.title');
  });

  it('should load create SipAccount page', async () => {
    await sipAccountComponentsPage.clickOnCreateButton();
    sipAccountUpdatePage = new SipAccountUpdatePage();
    expect(await sipAccountUpdatePage.getPageTitle()).to.eq('voipAdminApp.sipAccount.home.createOrEditLabel');
    await sipAccountUpdatePage.cancel();
  });

  /*  it('should create and save SipAccounts', async () => {
        const nbButtonsBeforeCreate = await sipAccountComponentsPage.countDeleteButtons();

        await sipAccountComponentsPage.clickOnCreateButton();
        await promise.all([
            sipAccountUpdatePage.setUsernameInput('username'),
            sipAccountUpdatePage.setPasswordInput('password'),
            sipAccountUpdatePage.setLineNumberInput('5'),
            sipAccountUpdatePage.pbxAccountSelectLastOption(),
            sipAccountUpdatePage.deviceSelectLastOption(),
        ]);
        expect(await sipAccountUpdatePage.getUsernameInput()).to.eq('username', 'Expected Username value to be equals to username');
        expect(await sipAccountUpdatePage.getPasswordInput()).to.eq('password', 'Expected Password value to be equals to password');
        const selectedLineEnabled = sipAccountUpdatePage.getLineEnabledInput();
        if (await selectedLineEnabled.isSelected()) {
            await sipAccountUpdatePage.getLineEnabledInput().click();
            expect(await sipAccountUpdatePage.getLineEnabledInput().isSelected(), 'Expected lineEnabled not to be selected').to.be.false;
        } else {
            await sipAccountUpdatePage.getLineEnabledInput().click();
            expect(await sipAccountUpdatePage.getLineEnabledInput().isSelected(), 'Expected lineEnabled to be selected').to.be.true;
        }
        expect(await sipAccountUpdatePage.getLineNumberInput()).to.eq('5', 'Expected lineNumber value to be equals to 5');
        const selectedIsManuallyCreated = sipAccountUpdatePage.getIsManuallyCreatedInput();
        if (await selectedIsManuallyCreated.isSelected()) {
            await sipAccountUpdatePage.getIsManuallyCreatedInput().click();
            expect(await sipAccountUpdatePage.getIsManuallyCreatedInput().isSelected(), 'Expected isManuallyCreated not to be selected').to.be.false;
        } else {
            await sipAccountUpdatePage.getIsManuallyCreatedInput().click();
            expect(await sipAccountUpdatePage.getIsManuallyCreatedInput().isSelected(), 'Expected isManuallyCreated to be selected').to.be.true;
        }
        await sipAccountUpdatePage.save();
        expect(await sipAccountUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await sipAccountComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /*  it('should delete last SipAccount', async () => {
        const nbButtonsBeforeDelete = await sipAccountComponentsPage.countDeleteButtons();
        await sipAccountComponentsPage.clickOnLastDeleteButton();

        sipAccountDeleteDialog = new SipAccountDeleteDialog();
        expect(await sipAccountDeleteDialog.getDialogTitle())
            .to.eq('voipAdminApp.sipAccount.delete.question');
        await sipAccountDeleteDialog.clickOnConfirmButton();

        expect(await sipAccountComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
