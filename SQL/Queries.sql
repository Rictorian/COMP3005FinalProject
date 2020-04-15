-- Most of these are a hybridization of the actual SQL query/update and Java code. This is because part of the extraction work
-- is done by the database queries while the remaining work is done in code to translate into a user experience.
-- This list is also not fully representitive of the SQL calls made to the database. The code contains many, many more.


-- For User checking. Data is then stripped off appropriately for the program. Most/All of the data was used so no SELECT specifications were made.
SELECT * FROM public.customer

-- Code fragment for adding the newly added customer info to register that customer
-- "INSERT INTO customer VALUES(" + newID + ", '" + name + "', '" + address + "', " + phoneNum + ", " + creditCard + ")"
INSERT INTO custoemr VALUES(4,'TestMan', '123 Street', 6136136113, 1234567890)

-- In charge of updating the user's basket if an existing record with the same ISBN is found under their UID. Increases the count on that record by 1.
-- "UPDATE basket SET quantity=quantity+1 WHERE ISBN='" + bookISBNList.get(int_choice-1) + "' AND UID=" + activeID
UPDATE basket SET quantity=quantity+1 WHERE ISBN=8141061211 AND UID=1001

-- Adds an entirely new tuple for basket if an existing record with the same ISBN and UID isn't found.
-- "INSERT INTO basket VALUES (" + activeID + ", '" + bookISBNList.get(int_choice-1) + "', " + 1 +")"
INSERT INTO basket VALUES (1001, '5340983596')

-- Used to get all records' order number to find the last order number used. This is incremented in code later to create the new order number. 
-- "SELECT order_number FROM orders"
SELECT order_number FROM orders

-- "SELECT address, credit_card_number FROM customer WHERE uid=" + activeID

-- "INSERT INTO orders VALUES (" + currOrderNum + ", " + activeID + ", 0000000, '" + resultSet3.getString("address") + "', " + resultSet3.getLong("credit_card_number"

-- "SELECT * FROM basket WHERE uid=" + activeID

-- "INSERT INTO orderbasket VALUES (" + resultSet2.getString("ISBN") + ", " + resultSet2.getInt("quantity") + ", " + currOrderNum + ");"

-- "DELETE FROM basket WHERE uid=" + activeID

