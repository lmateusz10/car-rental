import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CarRentalSystemSharedModule } from 'app/shared';
import {
    CarComponent,
    CarDetailComponent,
    CarUpdateComponent,
    CarDeletePopupComponent,
    CarDeleteDialogComponent,
    carRoute,
    carPopupRoute
} from './';

const ENTITY_STATES = [...carRoute, ...carPopupRoute];

@NgModule({
    imports: [CarRentalSystemSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [CarComponent, CarDetailComponent, CarUpdateComponent, CarDeleteDialogComponent, CarDeletePopupComponent],
    entryComponents: [CarComponent, CarUpdateComponent, CarDeleteDialogComponent, CarDeletePopupComponent],
    exports: [CarComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CarRentalSystemCarModule {}
