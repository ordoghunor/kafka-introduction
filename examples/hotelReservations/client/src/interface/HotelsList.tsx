import { Pagination } from './paginationInterface';

export interface HotelsList {
    hotelListings: HotelShort[];
    pagination: Pagination
}

export interface HotelShort {
    hotelId: number;
    name: string;
    latitude: number;
    longitude: number;
}
