ALTER TABLE TASK_LOG ADD COLUMN BANK_ID BIGINT;
ALTER TABLE TASK_LOG ADD CONSTRAINT BANK_ID_FK FOREIGN KEY(BANK_ID) REFERENCES BANK(ID) ON DELETE CASCADE;
