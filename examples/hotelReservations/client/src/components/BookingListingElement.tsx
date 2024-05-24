import { Alert, Button, Card, Col, Row } from "react-bootstrap"
import { BookingShort } from "../interface/BookingsList"
import { useState } from "react";
import configuredAxios from "../axios/configuredAxios";
import { useMutation } from "@tanstack/react-query";
import useAuth from "../hooks/useAuth";
import { AxiosError } from "axios";
import { ErrorResponseData } from "../interface/errorResponseInterface";

interface PropType {
  booking: BookingShort
}

export default function BookingListingElement({ booking }: PropType) {
  const [submitError, setSubmitError] = useState<string | null>(null);
  const [deleted, setDeleted] = useState<boolean>(false);
  const currentDate = new Date();
  const currentDatePlus2Hours = new Date(currentDate.getTime() + 2 * 60 * 60 * 1000);
  const startDate = new Date(booking.reservationStartDate);
  const canBeCanceled = startDate > currentDatePlus2Hours;
  const deleteUrl = `api/bookings/${booking.bookingId}`
  const { auth } = useAuth();

  const { mutate: mutateDelete } = useMutation({
    mutationFn: deleteMutationFunction,
    onSuccess: handleDeleteSubmitSucces,
    onError: handleSubmitError,
  });


  function handleDeleteButtonClicked() {
    if (auth.logged_in) {
      mutateDelete();
    } else {
      setSubmitError('Error: Cannot delete')
    }
  }

  function deleteMutationFunction() {
    return configuredAxios.delete(deleteUrl);
  }

  function handleDeleteSubmitSucces() {
    setDeleted(true);
  }

  function handleSubmitError(error: AxiosError<ErrorResponseData>) {
    if (error.message === 'Network Error') {
      setSubmitError('Error connecting to the server')
    } else {
      setSubmitError(error.response?.data.errorMessage || 'Error processing the request');
    }
  }




  if (deleted) {
    return
  }

  return (
    <>
      <Card className='mt-4 mb-3' >
        <Card.Header as='h5' key={`header_${booking.bookingId}`} >
          {booking.hotel.name}
        </Card.Header>
        <Card.Body>
          <Row key={`${booking.bookingId}_lat`} className="mt-1">
            <Col xs lg={2}>
              Latitude:
            </Col>
            <Col xs >
              {booking.hotel.latitude}
            </Col>
          </Row>
          <Row key={`${booking.bookingId}_long`} className="mt-1">
            <Col xs lg={2}>
              Longitude
            </Col>
            <Col xs >
              {booking.hotel.longitude}
            </Col>
          </Row>
          <Row key={`${booking.bookingId}_start`} className="mt-1">
            <Col xs lg={2}>
              Reservation from:
            </Col>
            <Col xs >
              {new Date(booking.reservationStartDate).toLocaleString('ro-RO')}
            </Col>
          </Row>
          <Row key={`${booking.bookingId}_end`} className="mt-1">
            <Col xs lg={2}>
              Reservation to:
            </Col>
            <Col xs >
              {new Date(booking.reservationEndDate).toLocaleString('ro-RO')}
            </Col>
          </Row>
          <Row>
            Rooms:
          </Row>
          {
            booking.rooms.length > 0 &&
            booking.rooms.map(currentRoom => (
              <Row>
                {currentRoom.type === 1 &&
                  <Col xs lg='2' key={`room_${currentRoom.roomId}_roomType_21`}>
                    Single Room
                  </Col>}
                {currentRoom.type === 2 &&
                  <Col xs lg='2' key={`room_${currentRoom.roomId}_roomType_22`}>
                    Double Room
                  </Col>}
                {currentRoom.type === 3 &&
                  <Col xs lg='2' key={`room_${currentRoom.roomId}_roomType_23`}>
                    Suite Room
                  </Col>}
                {currentRoom.type === 4 &&
                  <Col xs lg='2' key={`room_${currentRoom.roomId}_roomType_24`}>
                    Matrimonial Room
                  </Col>}
                <Col key={`room_${currentRoom.roomId}_room_number`}>
                  {`Number: ${currentRoom.roomNumber}`}
                </Col>
              </Row>
            ))
          }

          {canBeCanceled &&
            <Button className='mb-4 mt-4 btn btn-orange-dark' onClick={handleDeleteButtonClicked}>
              Cancel
            </Button>
          }
          <Alert key='danger' variant='danger' show={submitError !== null} className='mt-3'
            onClose={() => setSubmitError(null)} dismissible >
            {submitError}
          </Alert>

        </Card.Body>
      </Card>
    </>
  )
}
