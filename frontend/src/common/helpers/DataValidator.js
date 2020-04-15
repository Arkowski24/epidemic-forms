import { validatePolish } from 'validate-polish';

const validateTextField = (input) => input.length > 0;

const validateSliderField = (input, fieldSchema, minValue) => input >= minValue;

const validateChoiceField = (input) => input.filter((v) => v).length > 0;

const validatePassport = (passportNumber) => {
  const passportRegex = /^[A-Z]{2}[0-9]{7}$/;
  if (passportNumber.match(passportRegex) === null) return false;
  const weights = [7, 3, 9, 1, 7, 3, 1, 7, 3];
  const letters = [0, 1].map((i) => passportNumber.charCodeAt(i) - 55);
  const digits = [...Array(7).keys()].map((i) => Number(passportNumber.charAt(i + 2)));

  const result = letters
    .concat(digits)
    .map((d, i) => d * weights[i])
    .reduce((a, c) => a + c, 0);

  return result % 10 === 0;
};

const validateDerivedField = (input, index, derivedType) => {
  if (derivedType !== 'BIRTHDAY_PESEL') return input.length > 0;
  if (index !== 0) return input.length > 0;
  if (input.length === 0) return false;

  const field = JSON.parse(input);
  if (field.type === 'PESEL') return validatePolish.pesel(field.value);
  if (field.type === 'Nr dow. os.') return validatePolish.identityCard(field.value) || validatePolish.identityCardWithSeparator(field.value);
  if (field.type === 'Nr paszportu') return validatePassport(field.value);
  if (field.type === 'Inne') return field.value.length > 0;
  return field.value.length > 0;
};

export default {
  validateTextField, validateSliderField, validateChoiceField, validateDerivedField,
};
