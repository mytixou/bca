import { element, by, ElementFinder } from 'protractor';

export class TrancheAideEnfantGedComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-tranche-aide-enfant-ged div table .btn-danger'));
  title = element.all(by.css('jhi-tranche-aide-enfant-ged div h2#page-heading span')).first();
  noResult = element(by.id('no-result'));
  entities = element(by.id('entities'));

  async clickOnCreateButton(): Promise<void> {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(): Promise<void> {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons(): Promise<number> {
    return this.deleteButtons.count();
  }

  async getTitle(): Promise<string> {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class TrancheAideEnfantGedUpdatePage {
  pageTitle = element(by.id('jhi-tranche-aide-enfant-ged-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  idInput = element(by.id('field_id'));
  anneInput = element(by.id('field_anne'));
  moisInput = element(by.id('field_mois'));
  ageEnfantRevoluSurPeriodeInput = element(by.id('field_ageEnfantRevoluSurPeriode'));
  montantPlafondSalaireInput = element(by.id('field_montantPlafondSalaire'));
  isActifInput = element(by.id('field_isActif'));
  dateCreatedInput = element(by.id('field_dateCreated'));
  isUpdatedInput = element(by.id('field_isUpdated'));
  dateModifiedInput = element(by.id('field_dateModified'));

  strategieCmgGedSelect = element(by.id('field_strategieCmgGed'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setIdInput(id: string): Promise<void> {
    await this.idInput.sendKeys(id);
  }

  async getIdInput(): Promise<string> {
    return await this.idInput.getAttribute('value');
  }

  async setAnneInput(anne: string): Promise<void> {
    await this.anneInput.sendKeys(anne);
  }

  async getAnneInput(): Promise<string> {
    return await this.anneInput.getAttribute('value');
  }

  async setMoisInput(mois: string): Promise<void> {
    await this.moisInput.sendKeys(mois);
  }

  async getMoisInput(): Promise<string> {
    return await this.moisInput.getAttribute('value');
  }

  async setAgeEnfantRevoluSurPeriodeInput(ageEnfantRevoluSurPeriode: string): Promise<void> {
    await this.ageEnfantRevoluSurPeriodeInput.sendKeys(ageEnfantRevoluSurPeriode);
  }

  async getAgeEnfantRevoluSurPeriodeInput(): Promise<string> {
    return await this.ageEnfantRevoluSurPeriodeInput.getAttribute('value');
  }

  async setMontantPlafondSalaireInput(montantPlafondSalaire: string): Promise<void> {
    await this.montantPlafondSalaireInput.sendKeys(montantPlafondSalaire);
  }

  async getMontantPlafondSalaireInput(): Promise<string> {
    return await this.montantPlafondSalaireInput.getAttribute('value');
  }

  getIsActifInput(): ElementFinder {
    return this.isActifInput;
  }

  async setDateCreatedInput(dateCreated: string): Promise<void> {
    await this.dateCreatedInput.sendKeys(dateCreated);
  }

  async getDateCreatedInput(): Promise<string> {
    return await this.dateCreatedInput.getAttribute('value');
  }

  getIsUpdatedInput(): ElementFinder {
    return this.isUpdatedInput;
  }

  async setDateModifiedInput(dateModified: string): Promise<void> {
    await this.dateModifiedInput.sendKeys(dateModified);
  }

  async getDateModifiedInput(): Promise<string> {
    return await this.dateModifiedInput.getAttribute('value');
  }

  async strategieCmgGedSelectLastOption(): Promise<void> {
    await this.strategieCmgGedSelect.all(by.tagName('option')).last().click();
  }

  async strategieCmgGedSelectOption(option: string): Promise<void> {
    await this.strategieCmgGedSelect.sendKeys(option);
  }

  getStrategieCmgGedSelect(): ElementFinder {
    return this.strategieCmgGedSelect;
  }

  async getStrategieCmgGedSelectedOption(): Promise<string> {
    return await this.strategieCmgGedSelect.element(by.css('option:checked')).getText();
  }

  async save(): Promise<void> {
    await this.saveButton.click();
  }

  async cancel(): Promise<void> {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class TrancheAideEnfantGedDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-trancheAideEnfantGed-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-trancheAideEnfantGed'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
