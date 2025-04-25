# Absence, how it works: 
## Add a new Absence:
Absence can be added to any driver.
### Request:
-  Long driverId;
-  LocalDate dateStart;
-  LocalDate dateEnd;
-  Boolean withCar;
-  reason [VACATION, SICK_LEAVE];
-  String comment;

### Validation:  
- check if requested absence has time overlapping;
- check if requested absence's time interval overlaps with Balance calculated period;

## Update an existing Absence:
Existing Absence can be updated.
### Request:
-  Long id;
-  Long driverId;
-  LocalDate dateStart;
-  LocalDate dateEnd;
-  Boolean withCar;
-  reason [VACATION, SICK_LEAVE];
-  String comment;

### Validation:
- check if updated absence time interval has time overlapping with another absences;
- check if requested absence's time interval overlaps with Balance calculated period;

## Delete an existing Absence:
Existing Absence can be deleted.
### Request:
-  Long id;

### Validation:
- check if requested absence's time interval overlaps with Balance calculated period;

## Involvement into Rent Calculation:
Absences counted-in during Weekly Rent Calculation. The System selects a count of Absence-days that occur during calculated week.
In case of calculated week has 1 absence day, no Rent Amount adjustment required.
In case of calculated week has [2...6] absence days, new transaction with type "absence adjustment" will be created, to adjust the Financial Balance. 
The amount for this transaction is calculated by next formula: 

> **amount = (default-rent-mount /  6) * absence-days-count;**
