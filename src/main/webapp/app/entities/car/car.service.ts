import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICar } from 'app/shared/model/car.model';

type EntityResponseType = HttpResponse<ICar>;
type EntityArrayResponseType = HttpResponse<ICar[]>;

@Injectable({ providedIn: 'root' })
export class CarService {
    public resourceUrl = SERVER_API_URL + 'api/cars';

    constructor(protected http: HttpClient) {}

    create(car: ICar): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(car);
        return this.http
            .post<ICar>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(car: ICar): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(car);
        return this.http
            .put<ICar>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ICar>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ICar[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(car: ICar): ICar {
        const copy: ICar = Object.assign({}, car, {
            dateAdded: car.dateAdded != null && car.dateAdded.isValid() ? car.dateAdded.format(DATE_FORMAT) : null,
            dateModified: car.dateModified != null && car.dateModified.isValid() ? car.dateModified.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.dateAdded = res.body.dateAdded != null ? moment(res.body.dateAdded) : null;
            res.body.dateModified = res.body.dateModified != null ? moment(res.body.dateModified) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((car: ICar) => {
                car.dateAdded = car.dateAdded != null ? moment(car.dateAdded) : null;
                car.dateModified = car.dateModified != null ? moment(car.dateModified) : null;
            });
        }
        return res;
    }
}
