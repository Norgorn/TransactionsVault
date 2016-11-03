# TransactionsVault
Implementation of a test Java challenge

Transactions can be linked to each other (using a "parent_id") and it's needed to know the total amount involved for all transactions linked to a particular transaction.

RESTful сервис, который хранит транзакции в оперативной памяти (БД не используются). Транзакции можно добавлять или получать информацию о транзакции по ее ИД.

У транзакций есть тип и стоимость. Можно получить все транзакции определенного типа.

Транзакции могут быть связаны друг с другом с помощью ид родителя (parent_id), необходимо возвращать сумму значений всех транзакций, связанных с данной (то есть, сумму значений всех дочерних транзакций).
