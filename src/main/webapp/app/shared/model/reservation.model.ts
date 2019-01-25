import { Moment } from 'moment';
import { ICar } from 'app/shared/model//car.model';
import { IUser } from 'app/core/user/user.model';

export const enum PaymentStatus {
    NEW = 'NEW',
    PAID = 'PAID',
    CANCELED = 'CANCELED',
    REJECTED = 'REJECTED'
}

export interface IReservation {
    id?: number;
    startDate?: Moment;
    endDate?: Moment;
    paymentStatus?: PaymentStatus;
    car?: ICar;
    user?: IUser;
}

export class Reservation implements IReservation {
    constructor(
        public id?: number,
        public startDate?: Moment,
        public endDate?: Moment,
        public paymentStatus?: PaymentStatus,
        public car?: ICar,
        public user?: IUser
    ) {}
}
