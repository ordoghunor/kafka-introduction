import { Button, Card, Col, Row, Alert, Container } from "react-bootstrap"
import { useLocation, useNavigate } from 'react-router-dom'
import { useState } from "react"

import { HotelDetailedResponse } from "../../interface/HotelDetailedResponse"
import useAuth from "../../hooks/useAuth"
import { useMutation } from "@tanstack/react-query"
import { AxiosError } from "axios"
import { ErrorResponseData } from "../../interface/errorResponseInterface"
import configuredAxios from "../../axios/configuredAxios"


interface PropType {
  hotel: HotelDetailedResponse;
  startDate: string;
  endDate: string;
}

export default function HotelDetailed({ hotel, startDate, endDate }: PropType) {
  const { auth } = useAuth();
  const navigate = useNavigate();
  const location = useLocation();
  const bookingUrl = `/api/bookings`;
  const [selectedRooms, setSelectedRooms] = useState<number[]>([]);
  const [submitErrorBooking, setSubmitErrorBooking] = useState<string | null>(null);
  const [succesfullCreatedBooking, setSuccesfullCreatedBooking] = useState<boolean>(false);
  const { mutate: mutateBooking } = useMutation({
    mutationFn: mutationFunctionBooking,
    onSuccess: handleSubmitSuccesBooking,
    onError: handleSubmitErrorBooking,
  });

  function handleAddRoomClicked(roomId: number) {
    if (selectedRooms.includes(roomId)) {
      // remove the room from the selected ones
      setSelectedRooms(selectedRooms.filter(currentRoomId => currentRoomId !== roomId))
    } else {
      // add the room to the selected ones
      setSelectedRooms([...selectedRooms, roomId]);
    }
  }

  function handleReservationSubmit() {
    if (!auth.logged_in) {
      return;
    }
    mutateBooking();
  }

  function mutationFunctionBooking() {
    return configuredAxios.post(bookingUrl, {
      hotelId: hotel.hotelId,
      userId: auth.userId,
      startDate: new Date(startDate).toISOString(),
      endDate: new Date(endDate).toISOString(),
      rooms: selectedRooms
    });
  }

  function handleSubmitSuccesBooking() {
    if (!auth.logged_in) {
      navigate('/login');
    }
    setSubmitErrorBooking(null);
    setSuccesfullCreatedBooking(true);
  }

  function handleSubmitErrorBooking(error: AxiosError<ErrorResponseData>) {
    if (error.message === 'Network Error') {
      setSubmitErrorBooking('Error connecting to the server')
    } else {
      setSubmitErrorBooking(error.response?.data.errorMessage || 'Error creating Announcement');
    }
  }

  return (
    <>
      <Card className='mt-4 mb-3' key={`card_${hotel.hotelId}`}>
        <Card.Header as='h5' key={`header_${hotel.hotelId}`} >
          {hotel.name}
        </Card.Header>
        <Card.Body key={`hotel_${hotel.hotelId}_pos`}>
          <Row key={`${hotel.hotelId}_lat`} >
            <Col xs lg={2}>
              Latitude:
            </Col>
            <Col xs >
              {hotel.latitude}
            </Col>
          </Row>
          <Row key={`${hotel.hotelId}_long`} >
            <Col xs lg={2}>
              Longitude
            </Col>
            <Col xs >
              {hotel.longitude}
            </Col>
          </Row>
          <Row className="fs-5 my-3" key={`${hotel.hotelId}_rooms`}>
            Available rooms:
          </Row>
          {hotel.rooms.map(currentRoom => (
            <Card key={`room_${currentRoom.roomId}_card`}>
              <Card.Header key={`room_${currentRoom.roomId}_cardHeader`}>
                {auth.logged_in ?
                  <Button className="float-end btn btn-orange" onClick={() => handleAddRoomClicked(currentRoom.roomId)}
                    key={`room_${currentRoom.roomId}_add`}>
                    {selectedRooms.includes(currentRoom.roomId) ? 'Remove from Reservation' : 'Add to Reservation'}
                  </Button>
                  :
                  <Button className="float-end btn btn-orange" onClick={() => { navigate('/login', { state: { from: location }, replace: true }) }}
                    key={`room_${currentRoom.roomId}_login`}>
                    Log in to make a reservation
                  </Button>
                }
              </Card.Header>
              <Row key={`room_${currentRoom.roomId}_roomType`}>
                <Col xs lg={2} key={`room_${currentRoom.roomId}_roomType_1`}>
                  Room type:
                </Col>
                {currentRoom.type === 1 &&
                  <Col xs key={`room_${currentRoom.roomId}_roomType_21`}>
                    Single Room
                  </Col>}
                {currentRoom.type === 2 &&
                  <Col xs key={`room_${currentRoom.roomId}_roomType_22`}>
                    Double Room
                  </Col>}
                {currentRoom.type === 3 &&
                  <Col xs key={`room_${currentRoom.roomId}_roomType_23`}>
                    Suite Room
                  </Col>}
                {currentRoom.type === 4 &&
                  <Col xs key={`room_${currentRoom.roomId}_roomType_24`}>
                    Matrimonial Room
                  </Col>}
              </Row>
              <Row key={`room_${currentRoom.roomId}_price`}>
                <Col xs lg={2} key={`room_${currentRoom.roomId}_price_1`}>
                  Price
                </Col>
                <Col xs key={`room_${currentRoom.roomId}_price_2`}>
                  {currentRoom.price}
                </Col>
              </Row>
              <Row key={`room_${currentRoom.roomId}_roomNumber`}>
                <Col xs lg={2} key={`room_${currentRoom.roomId}_roomNumber_1`}>
                  Room number
                </Col>
                <Col xs key={`room_${currentRoom.roomId}_roomNumber_2`}>
                  {currentRoom.roomNumber}
                </Col>
              </Row>
            </Card>
          ))}
          {
            hotel.rooms.length < 1 &&
            <h4>No available rooms at the selected time interval, please try different dates.</h4>
          }
        </Card.Body>
        {auth.logged_in && hotel.rooms.length > 0 && selectedRooms.length !== 0 &&
          <Row>
            <Col lg={{ offset: 10, span: 2 }}>
              <Button className="btn btn-orange-dark" onClick={handleReservationSubmit} >
                Make Reservation
              </Button>
            </Col>
          </Row>
        }
        <Alert key='danger' variant='danger' show={submitErrorBooking !== null} className='mt-3'
          onClose={() => setSubmitErrorBooking(null)} dismissible >
          {submitErrorBooking}
        </Alert>
        <Alert key='success' variant='success' show={succesfullCreatedBooking} className='mt-3'
          onClose={() => setSuccesfullCreatedBooking(false)} dismissible >
          <Container>
            <Row>
              Booking succesfully created!
            </Row>
          </Container>
        </Alert>
      </Card>

    </>
  )
}
