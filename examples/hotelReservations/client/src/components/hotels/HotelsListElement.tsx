import { Card, Col, Row } from "react-bootstrap"
import { HotelShort } from "../../interface/HotelsList"
import { useNavigate } from "react-router-dom"


interface PropType {
  hotel: HotelShort
}

export default function HotelsListElement({ hotel }: PropType) {
  const navigate = useNavigate()

  return (
    <>
      <Card className='mt-4 mb-3 clickable' onClick={() => navigate(`/hotels/${hotel.hotelId}`)}>
        <Card.Header as='h5' key={`header_${hotel.hotelId}`} >
          {hotel.name}
        </Card.Header>
        <Card.Body>
          <Row key={`${hotel.hotelId}_lat`} className="fs-5">
            <Col xs lg={2}>
              Latitude:
            </Col>
            <Col xs >
              {hotel.latitude}
            </Col>
          </Row>
          <Row key={`${hotel.hotelId}_long`} className="fs-5">
            <Col xs lg={2}>
              Longitude
            </Col>
            <Col xs >
              {hotel.longitude}
            </Col>
          </Row>

        </Card.Body>
      </Card>
    </>
  )
}
