import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'beneficiaire',
        data: { pageTitle: 'bcaApp.beneficiaire.home.title' },
        loadChildren: () => import('./beneficiaire/beneficiaire.module').then(m => m.BeneficiaireModule),
      },
      {
        path: 'solde-ci',
        data: { pageTitle: 'bcaApp.soldeCi.home.title' },
        loadChildren: () => import('./solde-ci/solde-ci.module').then(m => m.SoldeCiModule),
      },
      {
        path: 'solde-apa',
        data: { pageTitle: 'bcaApp.soldeApa.home.title' },
        loadChildren: () => import('./solde-apa/solde-apa.module').then(m => m.SoldeApaModule),
      },
      {
        path: 'solde-pch',
        data: { pageTitle: 'bcaApp.soldePch.home.title' },
        loadChildren: () => import('./solde-pch/solde-pch.module').then(m => m.SoldePchModule),
      },
      {
        path: 'solde-pch-e',
        data: { pageTitle: 'bcaApp.soldePchE.home.title' },
        loadChildren: () => import('./solde-pch-e/solde-pch-e.module').then(m => m.SoldePchEModule),
      },
      {
        path: 'tiers-financeur',
        data: { pageTitle: 'bcaApp.tiersFinanceur.home.title' },
        loadChildren: () => import('./tiers-financeur/tiers-financeur.module').then(m => m.TiersFinanceurModule),
      },
      {
        path: 'aide',
        data: { pageTitle: 'bcaApp.aide.home.title' },
        loadChildren: () => import('./aide/aide.module').then(m => m.AideModule),
      },
      {
        path: 'strategie-ci',
        data: { pageTitle: 'bcaApp.strategieCi.home.title' },
        loadChildren: () => import('./strategie-ci/strategie-ci.module').then(m => m.StrategieCiModule),
      },
      {
        path: 'strategie-apa',
        data: { pageTitle: 'bcaApp.strategieApa.home.title' },
        loadChildren: () => import('./strategie-apa/strategie-apa.module').then(m => m.StrategieApaModule),
      },
      {
        path: 'strategie-pch',
        data: { pageTitle: 'bcaApp.strategiePch.home.title' },
        loadChildren: () => import('./strategie-pch/strategie-pch.module').then(m => m.StrategiePchModule),
      },
      {
        path: 'strategie-pch-e',
        data: { pageTitle: 'bcaApp.strategiePchE.home.title' },
        loadChildren: () => import('./strategie-pch-e/strategie-pch-e.module').then(m => m.StrategiePchEModule),
      },
      {
        path: 'nature-activite',
        data: { pageTitle: 'bcaApp.natureActivite.home.title' },
        loadChildren: () => import('./nature-activite/nature-activite.module').then(m => m.NatureActiviteModule),
      },
      {
        path: 'nature-montant',
        data: { pageTitle: 'bcaApp.natureMontant.home.title' },
        loadChildren: () => import('./nature-montant/nature-montant.module').then(m => m.NatureMontantModule),
      },
      {
        path: 'consommation-ci',
        data: { pageTitle: 'bcaApp.consommationCi.home.title' },
        loadChildren: () => import('./consommation-ci/consommation-ci.module').then(m => m.ConsommationCiModule),
      },
      {
        path: 'consommation-apa',
        data: { pageTitle: 'bcaApp.consommationApa.home.title' },
        loadChildren: () => import('./consommation-apa/consommation-apa.module').then(m => m.ConsommationApaModule),
      },
      {
        path: 'consommation-pch',
        data: { pageTitle: 'bcaApp.consommationPch.home.title' },
        loadChildren: () => import('./consommation-pch/consommation-pch.module').then(m => m.ConsommationPchModule),
      },
      {
        path: 'consommation-pch-e',
        data: { pageTitle: 'bcaApp.consommationPchE.home.title' },
        loadChildren: () => import('./consommation-pch-e/consommation-pch-e.module').then(m => m.ConsommationPchEModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
