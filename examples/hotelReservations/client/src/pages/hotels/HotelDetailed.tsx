import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { useQuery } from '@tanstack/react-query';
import { AxiosResponse } from 'axios';
import { FloatingLabel, Form, Stack } from 'react-bootstrap';

import configuredAxios from '../../axios/configuredAxios';
import { HotelDetailedResponse } from '../../interface/HotelDetailedResponse';
import HotelDetailedElement from '../../components/hotels/HotelDetailedElement';
import { startDateQuerryParamName, endDateQuerryParamName } from '../../config/application.json'


export default function HotelDetailed() {
  const { hotelId } = useParams();
  const [hotelUrl, setHotelUrl] = useState<string>(`/api/hotels/${hotelId}`);
  const currentDate = new Date();
  const tomorrowDate = new Date(currentDate);
  tomorrowDate.setDate(currentDate.getDate() + 1);
  const [startDate, setStartDate] = useState<string>(currentDate.toISOString().split('.')[0].slice(0, -3).toString());
  const [endDate, setEndDate] = useState<string>(tomorrowDate.toISOString().split('.')[0].slice(0, -3).toString());


  const { data: hotelData, isError, error, isLoading } = useQuery<AxiosResponse<HotelDetailedResponse>>({
    queryKey: ["hotelDetailed", hotelUrl],
    queryFn: queryFunction,
  });

  function queryFunction() {
    return configuredAxios.get(hotelUrl);
  }

  useEffect(() => {
    const queryParams = new URLSearchParams();
    if (startDate) {
      queryParams.set(startDateQuerryParamName, new Date(startDate).toISOString());
    }
    if (endDate) {
      queryParams.set(endDateQuerryParamName, new Date(endDate).toISOString());
    }
    setHotelUrl(`/api/hotels/${hotelId}?${queryParams.toString()}`)
  }, [
    startDate, endDate
  ]);

  if (isLoading) {
    return (
      <>
        <h2 className="text-center">Loading...</h2>
      </>
    )
  }

  if (isError) {
    return (
      <>
        <h2 className="error">{error.message || 'Sorry, there was an error!'}</h2>
      </>
    )
  }


  return (
    <>
      <h1>Hotel details</h1>

      <Stack direction="horizontal" gap={3} key={`dates_reservation_${hotelId}`}>
        <FloatingLabel
          label='Reservation start date' className='mb-3 mt-3' key={`start_date_reservation_label_${hotelId}`}>
          <Form.Control type='datetime-local' placeholder='Reservation start date'
            value={startDate} autoComplete='off'
            min={new Date().toISOString().split('.')[0].slice(0, -3).toString()}
            onChange={e => { setStartDate(e.target.value) }}
            key={`start_date_reservation_date_${hotelId}`} />
        </FloatingLabel>

        <FloatingLabel
          label='Reservation end date' className='mb-3 mt-3' key={`end_date_reservation_label_${hotelId}`}>
          <Form.Control type='datetime-local' placeholder='Reservation end date'
            value={endDate} autoComplete='off'
            min={new Date().toISOString().split('.')[0].slice(0, -3).toString()}
            onChange={e => { setEndDate(e.target.value) }}
            key={`end_date_reservation_date_${hotelId}`} />
        </FloatingLabel>

      </Stack>
      {hotelData?.data &&
        <HotelDetailedElement hotel={hotelData.data} startDate={startDate}
        endDate={endDate} key={`detals_${hotelId}`} />
      }
    </>
  )
}
