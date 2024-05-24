import { useNavigate } from 'react-router-dom'
import { Navbar, Nav, Container, NavDropdown } from 'react-bootstrap'

import useAuth from '../hooks/useAuth'
import configuredAxios from '../axios/configuredAxios';


export default function Navigationbar() {
  const navigate = useNavigate();
  const { auth, setAuth } = useAuth();

  function logout() {
    configuredAxios.get(`/logout`)
      .then(() => {
        setAuth({ username: undefined, logged_in: false, role: undefined, userId: undefined });
        navigate('/');
      })
      .catch((err) => {
        console.log(err);
      })
  }


  return (
    <Navbar collapseOnSelect expand='md' bg='customColor' sticky='top' className='px-5 mb-4' >
      <Container fluid className='mx-5 '>
        <Navbar.Brand className='ms-5 me-auto clickable' onClick={() => { navigate('/') }} >
          Hotel Reservations
        </Navbar.Brand>

        <Nav className='me-3' >
          <Nav.Link className='me-4 nav-text'
            onClick={() => { navigate('/hotels') }}>
            Hotels
          </Nav.Link>
          <Nav.Link className='me-4 nav-text'
            onClick={() => { navigate('/bookings') }}>
            Bookings
          </Nav.Link>
        </Nav>

        {!auth.logged_in &&
          <Nav className='me-3'>
            <Nav.Link className='me-4 nav-text border border-dark rounded-5 px-3 auth-button'
              onClick={() => { navigate('/login') }}>
              Login
            </Nav.Link>
            <Nav.Link className='me-3 nav-text border border-dark rounded-5 px-3 auth-button'
              onClick={() => { navigate('/register') }}>
              Create account
            </Nav.Link>
          </Nav>
        }
        {
          auth.logged_in &&
          <Nav className='me-3'>
            <Navbar.Text className='me-1 nav-text fw-bold'>
              Logged in as:
            </Navbar.Text>
            <NavDropdown title={auth.username} className='me-4 nav-text'>
              <NavDropdown.Item onClick={logout}>
                Logout
              </NavDropdown.Item>
            </NavDropdown>
          </Nav>
        }

      </Container>
    </Navbar>
  )
}