
const buildInitialRequest = (token) => ({ destination: `/app/requests/${token}`, body: JSON.stringify({ requestType: 'GET_STATE' }) });

export default { buildInitialRequest };
