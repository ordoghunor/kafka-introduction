import { HotelShort } from './HotelsList';
import { Room } from './Room';
import { Pagination } from './paginationInterface';

export interface BookingsList {
  bookingListingDtos: BookingShort[];
  pagination: Pagination
}

export interface BookingShort {
  bookingId: number;
  hotel: HotelShort;
  reservationStartDate: string;
  reservationEndDate: string;
  rooms: Room[];
}
