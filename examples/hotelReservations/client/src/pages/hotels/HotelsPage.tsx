import { Navigate, Route, Routes } from "react-router-dom";
import HotelsAll from "./HotelsAll";
import HotelDetailed from "./HotelDetailed";

export default function HotelsPage() {

  return (
    <>
      <Routes>
        <Route>
          <Route path='' element={<HotelsAll />} />
          <Route path='/:hotelId' element={<HotelDetailed />} />
          <Route path='*' element={<Navigate to="/" />} />
        </Route>
      </Routes>
    </>
  )
}
