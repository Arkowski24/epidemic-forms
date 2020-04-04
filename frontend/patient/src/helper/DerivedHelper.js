import { validatePolish } from 'validate-polish';

const deriveBirthdate = (pesel) => {
  let year = 1900 + Number(pesel.substr(0, 2));
  let month = Number(pesel.substr(2, 2));
  const day = Number(pesel.substr(4, 2));

  if (month > 80) {
    month -= 80;
    year -= 100;
  }
  if (month > 60) {
    month -= 60;
    year += 300;
  }
  if (month > 40) {
    month -= 40;
    year += 200;
  }
  if (month > 20) {
    month -= 20;
    year += 100;
  }

  return new Date(Date.UTC(year, month - 1, day))
    .toISOString()
    .split('T')[0];
};

const handleBirthdayPesel = (index, values) => {
  const newValues = values.slice();
  const pesel = values[0];
  if (validatePolish.pesel(pesel)) {
    newValues[1] = deriveBirthdate(pesel);
  }
  return newValues;
};

const calculateDerived = (derivedType, index, values) => {
  if (!values) return values;
  if (derivedType === 'BIRTHDAY_PESEL' && index === 0) {
    return handleBirthdayPesel(index, values);
  }
  return values;
};

export default { calculateDerived };
