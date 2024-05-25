import { useContext, useEffect, useState } from "react";
import { keepPreviousData, useQuery } from "@tanstack/react-query";

import { AxiosError, AxiosResponse } from "axios";
import { BookingsList } from "../interface/BookingsList";
import configuredAxios from "../axios/configuredAxios";
import {
  limitQuerryParamDefault, limitQuerryParamName,
  pageQuerryParamDefault, pageQuerryParamName, userQuerryParamName
} from '../config/application.json'
import { Navigate } from "react-router-dom";
import { Container } from "react-bootstrap";
import Limit from "../components/Limit";
import PaginationElement from "../components/PaginationElement";
import BookingListingElement from "../components/BookingListingElement";
import { SearchContext } from "../context/SearchContextProvider";
import useAuth from "../hooks/useAuth";


export default function BookingsAll() {
  const {auth} = useAuth();
  const [bookingsUrl, setBookingsUrl] = useState<string>(`/api/bookings?${userQuerryParamName}=${auth.userId}`);
  const { limit, page } = useContext(SearchContext);

  const { data: bookingsData, isError, error, isLoading } =
    useQuery<AxiosResponse<BookingsList>, AxiosError>({
      queryKey: ["bookingsList", bookingsUrl],
      queryFn: queryFunction,
      retry: false,
      placeholderData: keepPreviousData, // keeps the last succesful fetch as well beside current 
    });

  function queryFunction() {
    return configuredAxios.get(bookingsUrl);
  }


  useEffect(() => {
    if(!auth.logged_in || !auth.userId) {
      return;
    }
    const queryParams = new URLSearchParams();
    queryParams.set(userQuerryParamName, auth.userId.toString());

    if (limit !== limitQuerryParamDefault) {
      queryParams.set(limitQuerryParamName, limit);
    }
    if (page !== pageQuerryParamDefault) {
      queryParams.set(pageQuerryParamName, page);
    }
    setBookingsUrl(`/api/bookings?${queryParams.toString()}`)
  }, [
    limit, page, auth.userId
  ]);


  if (isLoading) {
    return (
      <>
        <h2 className="text-center">Loading...</h2>
      </>
    )
  }

  if (isError) {
    if (error.response?.status === 401) {
      return (
        <Navigate to='/login' state={{ from: location }} replace />
      )
    }
    return (
      <>
        <h2 className="error">{error.message || 'Sorry, there was an error!'}</h2>
      </>
    )
  }


  return (
    <>
      <h1>Bookings</h1>
      <Container>
        <h3 className="mb-3">Found {bookingsData?.data.pagination.totalCount} results</h3>
        <Limit />
        {
          bookingsData?.data &&
            bookingsData?.data.bookingListingDtos.length > 0 ? (
            <Container>
              {bookingsData?.data.bookingListingDtos.map(currentElement => (
                <BookingListingElement booking={currentElement} key={`${currentElement.bookingId}`} />

              ))}
              < PaginationElement totalPages={bookingsData?.data.pagination.totalPages} />
            </Container>
          )
            :
            <h3>No Bookings found!</h3>
        }
      </Container>
    </>
  )
}
