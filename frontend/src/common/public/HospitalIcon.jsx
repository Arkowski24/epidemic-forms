import React from 'react';

import { Image } from 'react-bootstrap';
import hospitalLogo from './hospital_logo.png';


const HospitalIcon = ({ height }) => (
  <Image src={hospitalLogo} style={{ height }} alt="Logo of the hospital" fluid />
);

export default HospitalIcon;
