import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  ResponsiblePersonComponentsPage,
  ResponsiblePersonDeleteDialog,
  ResponsiblePersonUpdatePage
} from './responsible-person.page-object';

const expect = chai.expect;

describe('ResponsiblePerson e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let responsiblePersonComponentsPage: ResponsiblePersonComponentsPage;
  let responsiblePersonUpdatePage: ResponsiblePersonUpdatePage;
  let responsiblePersonDeleteDialog: ResponsiblePersonDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ResponsiblePeople', async () => {
    await navBarPage.goToEntity('responsible-person');
    responsiblePersonComponentsPage = new ResponsiblePersonComponentsPage();
    await browser.wait(ec.visibilityOf(responsiblePersonComponentsPage.title), 5000);
    expect(await responsiblePersonComponentsPage.getTitle()).to.eq('voipAdminApp.responsiblePerson.home.title');
  });

  it('should load create ResponsiblePerson page', async () => {
    await responsiblePersonComponentsPage.clickOnCreateButton();
    responsiblePersonUpdatePage = new ResponsiblePersonUpdatePage();
    expect(await responsiblePersonUpdatePage.getPageTitle()).to.eq('voipAdminApp.responsiblePerson.home.createOrEditLabel');
    await responsiblePersonUpdatePage.cancel();
  });

  it('should create and save ResponsiblePeople', async () => {
    const nbButtonsBeforeCreate = await responsiblePersonComponentsPage.countDeleteButtons();

    await responsiblePersonComponentsPage.clickOnCreateButton();
    await promise.all([
      responsiblePersonUpdatePage.setCodeInput('code'),
      responsiblePersonUpdatePage.setFirstNameInput('firstName'),
      responsiblePersonUpdatePage.setMiddleNameInput('middleName'),
      responsiblePersonUpdatePage.setLastNameInput('lastName'),
      responsiblePersonUpdatePage.setPositionInput('position'),
      responsiblePersonUpdatePage.setDepartmentInput('department'),
      responsiblePersonUpdatePage.setLocationInput('location')
    ]);
    expect(await responsiblePersonUpdatePage.getCodeInput()).to.eq('code', 'Expected Code value to be equals to code');
    expect(await responsiblePersonUpdatePage.getFirstNameInput()).to.eq('firstName', 'Expected FirstName value to be equals to firstName');
    expect(await responsiblePersonUpdatePage.getMiddleNameInput()).to.eq(
      'middleName',
      'Expected MiddleName value to be equals to middleName'
    );
    expect(await responsiblePersonUpdatePage.getLastNameInput()).to.eq('lastName', 'Expected LastName value to be equals to lastName');
    expect(await responsiblePersonUpdatePage.getPositionInput()).to.eq('position', 'Expected Position value to be equals to position');
    expect(await responsiblePersonUpdatePage.getDepartmentInput()).to.eq(
      'department',
      'Expected Department value to be equals to department'
    );
    expect(await responsiblePersonUpdatePage.getLocationInput()).to.eq('location', 'Expected Location value to be equals to location');
    await responsiblePersonUpdatePage.save();
    expect(await responsiblePersonUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await responsiblePersonComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last ResponsiblePerson', async () => {
    const nbButtonsBeforeDelete = await responsiblePersonComponentsPage.countDeleteButtons();
    await responsiblePersonComponentsPage.clickOnLastDeleteButton();

    responsiblePersonDeleteDialog = new ResponsiblePersonDeleteDialog();
    expect(await responsiblePersonDeleteDialog.getDialogTitle()).to.eq('voipAdminApp.responsiblePerson.delete.question');
    await responsiblePersonDeleteDialog.clickOnConfirmButton();

    expect(await responsiblePersonComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
