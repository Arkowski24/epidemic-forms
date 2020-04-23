# COVID-19 epidemic form

Limiting patient-personnel contact is one of the most important safety measures. 

The projects aim to eliminate paper forms used during an epidemic interview and replace them with a digital one, filled on a tablet that can be easily decontaminated afterward. 
The answers are conveniently saved to the PDF files that can be printed if necessary.

## To start using the project

The project requires [Docker Compose](https://docs.docker.com/compose/).


### (Optional) Step 0

If you want to use the project on multiple devices, you have to modify the API URL used by the frontend to match your server IP or domain name.

Open `frontend/src/config.js` and modify the content accordingly:

```javascript
const ROOT_ADDRESS = '<YOUR_SERVER_ADDRESS>';
```

You can access your IP address using `ip addr s` (Linux) or `ipconfig` (Windows).

### Step 1

Clone the repository and start the application using docker-compose:

```bash
git clone https://github.com/Arkowski24/covid-19-tablet.git
cd covid-19-tablet 
docker-compose up
```

### Step 2

There are three accounts created by default:
* **Admin** - username: *admin*, password: *123456*
* **Employee** - username: *employee*, password: *123456*
* **Device** - username: *device*, password: *123456*

You can create new ones, and delete the initial ones afterwards.

You can access patient part under `http://localhost/` 
and the employee one under `http://localhost/employee/`.

To access the device panel in the patient part, click on the hospital logo 5 times. After a successful login, the continuous mode is enabled, which enables employees to send forms directly to the device.
