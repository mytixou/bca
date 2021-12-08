import { element, by, ElementFinder } from 'protractor';

export class StrategiePchEComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-strategie-pch-e div table .btn-danger'));
  title = element.all(by.css('jhi-strategie-pch-e div h2#page-heading span')).first();
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

export class StrategiePchEUpdatePage {
  pageTitle = element(by.id('jhi-strategie-pch-e-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  idInput = element(by.id('field_id'));
  isActifInput = element(by.id('field_isActif'));
  dateMensuelleDebutValiditeInput = element(by.id('field_dateMensuelleDebutValidite'));
  anneInput = element(by.id('field_anne'));
  moisInput = element(by.id('field_mois'));
  montantPlafondSalaireInput = element(by.id('field_montantPlafondSalaire'));
  montantPlafondCotisationsInput = element(by.id('field_montantPlafondCotisations'));
  montantPlafondSalairePlusInput = element(by.id('field_montantPlafondSalairePlus'));
  montantPlafondCotisationsPlusInput = element(by.id('field_montantPlafondCotisationsPlus'));
  nbHeureSalairePlafondInput = element(by.id('field_nbHeureSalairePlafond'));
  tauxSalaireInput = element(by.id('field_tauxSalaire'));
  tauxCotisationsInput = element(by.id('field_tauxCotisations'));

  aideSelect = element(by.id('field_aide'));
  tiersFinanceurSelect = element(by.id('field_tiersFinanceur'));
  natureActiviteSelect = element(by.id('field_natureActivite'));
  natureMontantSelect = element(by.id('field_natureMontant'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setIdInput(id: string): Promise<void> {
    await this.idInput.sendKeys(id);
  }

  async getIdInput(): Promise<string> {
    return await this.idInput.getAttribute('value');
  }

  getIsActifInput(): ElementFinder {
    return this.isActifInput;
  }

  async setDateMensuelleDebutValiditeInput(dateMensuelleDebutValidite: string): Promise<void> {
    await this.dateMensuelleDebutValiditeInput.sendKeys(dateMensuelleDebutValidite);
  }

  async getDateMensuelleDebutValiditeInput(): Promise<string> {
    return await this.dateMensuelleDebutValiditeInput.getAttribute('value');
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

  async setMontantPlafondSalaireInput(montantPlafondSalaire: string): Promise<void> {
    await this.montantPlafondSalaireInput.sendKeys(montantPlafondSalaire);
  }

  async getMontantPlafondSalaireInput(): Promise<string> {
    return await this.montantPlafondSalaireInput.getAttribute('value');
  }

  async setMontantPlafondCotisationsInput(montantPlafondCotisations: string): Promise<void> {
    await this.montantPlafondCotisationsInput.sendKeys(montantPlafondCotisations);
  }

  async getMontantPlafondCotisationsInput(): Promise<string> {
    return await this.montantPlafondCotisationsInput.getAttribute('value');
  }

  async setMontantPlafondSalairePlusInput(montantPlafondSalairePlus: string): Promise<void> {
    await this.montantPlafondSalairePlusInput.sendKeys(montantPlafondSalairePlus);
  }

  async getMontantPlafondSalairePlusInput(): Promise<string> {
    return await this.montantPlafondSalairePlusInput.getAttribute('value');
  }

  async setMontantPlafondCotisationsPlusInput(montantPlafondCotisationsPlus: string): Promise<void> {
    await this.montantPlafondCotisationsPlusInput.sendKeys(montantPlafondCotisationsPlus);
  }

  async getMontantPlafondCotisationsPlusInput(): Promise<string> {
    return await this.montantPlafondCotisationsPlusInput.getAttribute('value');
  }

  async setNbHeureSalairePlafondInput(nbHeureSalairePlafond: string): Promise<void> {
    await this.nbHeureSalairePlafondInput.sendKeys(nbHeureSalairePlafond);
  }

  async getNbHeureSalairePlafondInput(): Promise<string> {
    return await this.nbHeureSalairePlafondInput.getAttribute('value');
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

  async natureActiviteSelectLastOption(): Promise<void> {
    await this.natureActiviteSelect.all(by.tagName('option')).last().click();
  }

  async natureActiviteSelectOption(option: string): Promise<void> {
    await this.natureActiviteSelect.sendKeys(option);
  }

  getNatureActiviteSelect(): ElementFinder {
    return this.natureActiviteSelect;
  }

  async getNatureActiviteSelectedOption(): Promise<string> {
    return await this.natureActiviteSelect.element(by.css('option:checked')).getText();
  }

  async natureMontantSelectLastOption(): Promise<void> {
    await this.natureMontantSelect.all(by.tagName('option')).last().click();
  }

  async natureMontantSelectOption(option: string): Promise<void> {
    await this.natureMontantSelect.sendKeys(option);
  }

  getNatureMontantSelect(): ElementFinder {
    return this.natureMontantSelect;
  }

  async getNatureMontantSelectedOption(): Promise<string> {
    return await this.natureMontantSelect.element(by.css('option:checked')).getText();
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

export class StrategiePchEDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-strategiePchE-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-strategiePchE'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
