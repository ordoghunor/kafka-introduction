import { Room } from "./Room";

export interface HotelDetailedResponse {
  hotelId: number;
  name: string;
  latitude: number;
  longitude: number;
  rooms: Room[]
}
