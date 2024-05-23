import { Navigate, Route, Routes } from "react-router-dom";

import BookingsAll from "./BookingsAll";
import RequireAuth from "../components/RequireAuth";

export default function BookingsPage() {
  return (
    <>
      <Routes>
        <Route>
          <Route element={<RequireAuth />}>
            <Route path='' element={<BookingsAll />} />
          </Route>
          <Route path='*' element={<Navigate to="/" />} />
        </Route>
      </Routes>
    </>
  )
}
