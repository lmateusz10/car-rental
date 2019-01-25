import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CarRentalSystemSharedModule } from 'app/shared';
import { CarRentalSystemAdminModule } from 'app/admin/admin.module';
import {
    ReservationComponent,
    ReservationDetailComponent,
    ReservationUpdateComponent,
    ReservationDeletePopupComponent,
    ReservationDeleteDialogComponent,
    reservationRoute,
    reservationPopupRoute
} from './';

const ENTITY_STATES = [...reservationRoute, ...reservationPopupRoute];

@NgModule({
    imports: [CarRentalSystemSharedModule, CarRentalSystemAdminModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ReservationComponent,
        ReservationDetailComponent,
        ReservationUpdateComponent,
        ReservationDeleteDialogComponent,
        ReservationDeletePopupComponent
    ],
    entryComponents: [ReservationComponent, ReservationUpdateComponent, ReservationDeleteDialogComponent, ReservationDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CarRentalSystemReservationModule {}
