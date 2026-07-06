import './home.scss';

import React from 'react';
import { Col, Row } from 'react-bootstrap';
// import { Translate } from 'react-jhipster';
// import { Link } from 'react-router';
// import { useAppSelector } from 'app/config/store';
export const Home = () => {
  return (
    <Row>
      <Col md="12">
        <h1 className="display-4">Bienvenue dans le stockage matériel de Jolof Enterprises!</h1>
      </Col>
    </Row>
  );
};

export default Home;
