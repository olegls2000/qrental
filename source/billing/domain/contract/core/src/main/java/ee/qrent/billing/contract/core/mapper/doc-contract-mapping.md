# Mapping for the Contract attributes:

| Contract attribute              | Person                | Self_Empl                        | LHV                     | Company                          |
|---------------------------------|-----------------------|----------------------------------|-------------------------|----------------------------------|
| String renter                   | driver.fn + driver.ln | driver.fn + driver.ln + 'FIE'    | driver.fn + driver.ln   | driver.companyName               |
| String renterRegistrationNumber | driver.taxNumber      | driver.companyRegistrationNumber | driver.taxNumber        | driver.companyRegistrationNumber |
| String renterSignerName         | driver.fn + driver.ln | driver.fn + driver.ln            | driver.fn + driver.ln   | driver.companyCeoName            |
| String renterSignerTaxNumber    | driver.taxNumber      | driver.taxNumber                 | driver.taxNumber        | driver.companyCeoTaxNumber       |
| String renterAddress            | driver.address        | driver.address                   | driver.address          | driver.companyAddress            |
