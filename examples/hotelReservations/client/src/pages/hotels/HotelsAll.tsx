import { useContext, useEffect, useState } from "react"
import { keepPreviousData, useQuery } from "@tanstack/react-query";
import { AxiosError, AxiosResponse } from "axios";
import { Container, FloatingLabel, Stack, Form, Button } from "react-bootstrap";
import { Navigate } from "react-router-dom";

import { SearchContext } from "../../context/SearchContextProvider";
import configuredAxios from "../../axios/configuredAxios";
import { HotelsList } from "../../interface/HotelsList";
import {
  limitQuerryParamDefault, limitQuerryParamName,
  pageQuerryParamDefault, pageQuerryParamName,
  distanceParamDefault, distanceQuerryParamName,
  latQuerryParamName, longQuerryParamName,
  latDefaultValue, longDefaultValue
} from '../../config/application.json'
import Limit from "../../components/Limit";
import PaginationElement from "../../components/PaginationElement";
import HotelsListElement from "../../components/hotels/HotelsListElement";


export default function HotelsAll() {
  const [hotelsUrl, setHotelsUrl] = useState<string>('/api/hotels');
  const [distanceKm, setDistanceKm] = useState<number>(distanceParamDefault);
  const [distanceKmError, setDistanceKmError] = useState<string>('');
  const [currentDistanceKm, setCurrentDistanceKm] = useState<number>(distanceParamDefault);
  const { limit, page } = useContext(SearchContext);
  const [userLocation, setUserLocation] = useState<[number, number]>([latDefaultValue, longDefaultValue]);
  const [locationAvailable, setLocationAvailable] = useState(false);


  const { data: hotelsData, isError, error, isLoading } =
    useQuery<AxiosResponse<HotelsList>, AxiosError>({
      queryKey: ["hotelsList", hotelsUrl],
      queryFn: queryFunction,
      retry: false,
      placeholderData: keepPreviousData, // keeps the last succesful fetch as well beside current 
    });

  function queryFunction() {
    return configuredAxios.get(hotelsUrl);
  }

  // get user position
  useEffect(() => {
    if (!locationAvailable) {
      // update position if location of user is available
      navigator.geolocation.getCurrentPosition((position) => {
        setUserLocation([
          position.coords.latitude,
          position.coords.longitude
        ]);
        setLocationAvailable(true);
      },
        (err) => {
          if (err.code === 1) {
            // user denied location
          } else {
            console.log(err.message);
          }
        }
      );
    }
  }, [locationAvailable])


  useEffect(() => {
    const queryParams = new URLSearchParams();
    if (limit !== limitQuerryParamDefault) {
      queryParams.set(limitQuerryParamName, limit);
    }
    if (page !== pageQuerryParamDefault) {
      queryParams.set(pageQuerryParamName, page);
    }
    if (userLocation[0] !== latDefaultValue) {
      queryParams.set(latQuerryParamName, userLocation[0].toString());
    }
    if (userLocation[1] !== longDefaultValue) {
      queryParams.set(longQuerryParamName, userLocation[1].toString());
    }
    if (distanceKm < 1) {
      setDistanceKmError('Distance must be a number above 1');
    } else {
      setDistanceKmError('');
      queryParams.set(distanceQuerryParamName, distanceKm.toString());
    }

    setHotelsUrl(`/api/hotels?${queryParams.toString()}`)
  }, [
    limit, page, distanceKm, userLocation
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
      <h1>Hotels</h1>
      <Stack direction="horizontal" className="me-5" gap={3}>
        <Form.Text as='h4' className="ms-3">
          Search at maximum km:
        </Form.Text>
        <FloatingLabel
          label='Distance in km' className='mb-3 mt-2' >
          <Form.Control type='number' min={1} placeholder='Price'
            value={currentDistanceKm} autoComplete='off' isInvalid={!!distanceKmError}
            onChange={e => { setCurrentDistanceKm(parseInt(e.target.value)) }} />
        </FloatingLabel>
        <Button className="btn btn-orange-dark" onClick={() => setDistanceKm(currentDistanceKm)}>
          Update distance
        </Button>
      </Stack>
      <Container>
        <h3 className="mb-3">Found {hotelsData?.data.pagination.totalCount} results</h3>
        <Limit />
        {
          hotelsData?.data.hotelListings &&
            hotelsData?.data.hotelListings.length > 0 ? (
            <Container>
              {hotelsData?.data.hotelListings.map(currentElement => (
                <HotelsListElement hotel={currentElement} key={currentElement.hotelId} />

              ))}
              < PaginationElement totalPages={hotelsData?.data.pagination.totalPages} />
            </Container>
          )
            :
            <h3>No Hotels found!</h3>
        }
      </Container>
    </>
  )
}
