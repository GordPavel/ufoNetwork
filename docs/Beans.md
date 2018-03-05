**Лабораторная работа №4**

Краткое описание задачи
Расширить функциональность написанного ранее приложения возможностью описания объектов, хранящихся в базе данных, в виде XML документов.
Реализацию написать с использованием Session Beans. Структура XML документа должна описываться с помощью DTD пли XSD. которые строятся на основании определенных в системе сущностей и их атрибутов. 
Полученное описание объекта в виде XML нужно отображать в браузере в виде HTML, полученное в результате трансформации XML с использованием XSLT.

Функции программы
1.	Для преобразования информации об объектах в XML нужно использовать Stateless Session Beans.
2.	Выборка объектов должна осуществляться по определенному критерию (критериев может быть несколько, и пользователь может вручную выбрать какой критерии использовать). 
Выбранные в соответствии с критерием, объекты должны быть описаны XML-документом. В качестве критерия для выбора объекта можно использовать поиск но идентификатору объекта (ID), 
по имени, по каким-либо связям, определенным для этого тина объекта.
3.	Полученный XML документ должен быть сохранен в файл или в базу данных.
4.	Должна быть реализована возможность использовать полученный в результате экспорта XML документ тля импорта объектов на другой сервер (пли на тот же). 
Т.е. объекты из XML документа можно восстановить в б,п\ данных (обратная операция к операции, проделанной в предыдущих пунктах).

Дополнительные функции программы
5.	Для наглядного отображения экспортируемых объектов написать страницу, которая будет преобразовывать XML в HTML при помощи XLST.
6.	Сделать логику импорта управляемой. То есть в случае, когда импортируемый объект уже имеется в базе, должны быть предусмотрены следующие действия: 
«затирание» старых параметров новыми. mi норирование данных из файла импорта и вывод сообщения о том. что такой объект уже есть в базе данных.
7.	Реализовать возможность автоматического поиска объектов для экспорта, которые связаны с экспортируемым объектом (например, если экспортируется Игрок, 
то вместе с ним должна экспортироваться Команда, которой этот игрок принадлежит). Также должны экспортироваться все объекты, лежащие ниже но иерархии, 
т.е. являющиеся детьми данного объекта. Возможны и другие варианты поиска зависимых объектов.

Технические требования
1.	Создать DTD или XSD файл, описывающий формат XML документа для импорта и экспорта. Написать Stateless Session Bean, который будет реализовывать процедуру импорта и экспорта сущностей. 
При импорте должна выполняться проверка на соответствие входных данных файлам описания (DTD/XSD).
2.	В методе импорта необходимо для каждого объекта выполнять проверку на наличие его в хранилище, чтобы не вызывать нарушения целостности путем попытки создания второго объекта с таким же первичным ключом. 
Если объект уже существует, то в зависимости от значения входных параметров выполняем обновление параметров объекта, выводим сообщение или другие действия.
3.	Для экспортирования группы объектов должен быть написан метод, который обрабатывает коллекцию смшюстей и генерирует XML документ экспорта, содержащий всю запрошенную информацию об этих объектах. 
Для этого можно передавать в качестве параметра вызова коллекцию первичных ключей объектов для экспорта, либо же реализовать механизм, когда Session Bean вызывает методы поиска DAO-объекта.
Дополнительные технические требования
4.	При программировании возможности автоматического поиска объектов для экспорта, которые связаны с экспортируемой сущностью, 
сам механизм поиска следует делать универсальным - связи для поиска должны задаваться в отдельном конфигурационном файле. Это необходимо для повышения гибкости программы.