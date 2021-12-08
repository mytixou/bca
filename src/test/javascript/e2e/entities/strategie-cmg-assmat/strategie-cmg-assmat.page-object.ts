import { element, by, ElementFinder } from 'protractor';

export class StrategieCmgAssmatComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-strategie-cmg-assmat div table .btn-danger'));
  title = element.all(by.css('jhi-strategie-cmg-assmat div h2#page-heading span')).first();
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

export class StrategieCmgAssmatUpdatePage {
  pageTitle = element(by.id('jhi-strategie-cmg-assmat-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  idInput = element(by.id('field_id'));
  anneInput = element(by.id('field_anne'));
  moisInput = element(by.id('field_mois'));
  nbHeureSeuilPlafondInput = element(by.id('field_nbHeureSeuilPlafond'));
  tauxSalaireInput = element(by.id('field_tauxSalaire'));
  tauxCotisationsInput = element(by.id('field_tauxCotisations'));
  isActifInput = element(by.id('field_isActif'));
  dateCreatedInput = element(by.id('field_dateCreated'));
  isUpdatedInput = element(by.id('field_isUpdated'));
  dateModifiedInput = element(by.id('field_dateModified'));

  aideSelect = element(by.id('field_aide'));
  tiersFinanceurSelect = element(by.id('field_tiersFinanceur'));

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

  async setNbHeureSeuilPlafondInput(nbHeureSeuilPlafond: string): Promise<void> {
    await this.nbHeureSeuilPlafondInput.sendKeys(nbHeureSeuilPlafond);
  }

  async getNbHeureSeuilPlafondInput(): Promise<string> {
    return await this.nbHeureSeuilPlafondInput.getAttribute('value');
  }

  async setTauxSalaireInput(tauxSalaire: string): Promise<void> {
    await this.tauxSalaireInput.sendKeys(tauxSalaire);
  }

  async getTauxSalaireInput(): Promise<string> {
    return await this.tauxSalaireInput.getAttribute('value');
  }

  async setTauxCotisationsInput(tauxCotisations: string): Promise<void> {
    await this.tauxCotisationsInput.sendKeys(tauxCotisations);
  }

  async getTauxCotisationsInput(): Promise<string> {
    return await this.tauxCotisationsInput.getAttribute('value');
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

  async aideSelectLastOption(): Promise<void> {
    await this.aideSelect.all(by.tagName('option')).last().click();
  }

  async aideSelectOption(option: string): Promise<void> {
    await this.aideSelect.sendKeys(option);
  }

  getAideSelect(): ElementFinder {
    return this.aideSelect;
  }

  async getAideSelectedOption(): Promise<string> {
    return await this.aideSelect.element(by.css('option:checked')).getText();
  }

  async tiersFinanceurSelectLastOption(): Promise<void> {
    await this.tiersFinanceurSelect.all(by.tagName('option')).last().click();
  }

  async tiersFinanceurSelectOption(option: string): Promise<void> {
    await this.tiersFinanceurSelect.sendKeys(option);
  }

  getTiersFinanceurSelect(): ElementFinder {
    return this.tiersFinanceurSelect;
  }

  async getTiersFinanceurSelectedOption(): Promise<string> {
    return await this.tiersFinanceurSelect.element(by.css('option:checked')).getText();
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

export class StrategieCmgAssmatDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-strategieCmgAssmat-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-strategieCmgAssmat'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
