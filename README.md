# Covid_clinic
A class hierarchy for Java implementing the management of a health clinic dealing with Covid-19 patients. 
## Table of contents

- Specifications
- Technologies


## Specifications
Any user can login as an administrator, technician or nurse, which grants permision to different features within the aplication.
### Administrator
This option section allows the user to view the lists of patients, workers and tests of the clinic, as opposed to nurses and lab technicians who only visualize patients and assigned tests. These lists cover the entire clinic and display all the data of the object in question. Patients may be infected, confined, immunized or vaccinated.
### Nurse
They are the ones performing the tests previously asigned by lab technicians
### Technician
They are the ones analizing the tests carried out by the nurses. Once the test is finished, they register the results in the system, which updates the status of the patients.
## Technologies
This class hierarchy was created with Eclipse IDE verion 2021-03
