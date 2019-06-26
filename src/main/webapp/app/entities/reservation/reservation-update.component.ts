import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, ActivatedRouteSnapshot, Route } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';

import { IReservation } from 'app/shared/model/reservation.model';
import { ReservationService } from './reservation.service';
import { Car, ICar } from 'app/shared/model/car.model';
import { CarService } from 'app/entities/car';
import { AccountService, IUser, User, UserService } from 'app/core';
import { filter, map } from 'rxjs/operators';
import { NgbTimeStruct } from '@ng-bootstrap/ng-bootstrap';
@Component({
    selector: 'jhi-reservation-update',
    templateUrl: './reservation-update.component.html'
})
export class ReservationUpdateComponent implements OnInit {
    reservation: IReservation;
    isSaving: boolean;

    car: ICar;

    cars: ICar[];

    users: IUser[];

    user: any;
    startDateDp: any;
    endDateDp: any;

    constructor(
        protected accountService: AccountService,
        protected jhiAlertService: JhiAlertService,
        protected reservationService: ReservationService,
        protected carService: CarService,
        protected userService: UserService,
        protected activatedRoute: ActivatedRoute //protected route: ActivatedRouteSnapshot
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ reservation }) => {
            this.reservation = reservation;
        });

        let carId = parseInt(this.activatedRoute.snapshot.paramMap.get('carId'));

        if (carId) {
            this.carService
                .find(carId)
                .pipe(
                    filter((response: HttpResponse<Car>) => response.ok),
                    map((car: HttpResponse<Car>) => (this.car = car.body))
                )
                .subscribe(res => {
                    this.car = res;
                    this.renderCarInfo();
                });
        }

        this.carService.query().subscribe(
            (res: HttpResponse<ICar[]>) => {
                this.cars = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.userService.query().subscribe(
            (res: HttpResponse<IUser[]>) => {
                this.users = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );

        this.accountService.identity().then(
            x => {
                this.user = x;
            },
            y => (this.user = 0)
        );
    }
    renderCarInfo() {
        this.car;
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.reservation.user = this.user;

        this.reservation.car = this.car;
        this.isSaving = true;
        if (this.reservation.id !== undefined) {
            this.subscribeToSaveResponse(this.reservationService.update(this.reservation));
        } else {
            this.subscribeToSaveResponse(this.reservationService.create(this.reservation));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IReservation>>) {
        result.subscribe((res: HttpResponse<IReservation>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackCarById(index: number, item: ICar) {
        return item.id;
    }

    trackUserById(index: number, item: IUser) {
        return item.id;
    }
}
