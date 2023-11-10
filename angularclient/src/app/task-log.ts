import { Employee } from './employee';
import { TaskType } from './task-type';
import { Bank } from './bank';
export class TaskLog {
    id: string;
    employee: Employee;
    taskType: TaskType;
    bank: Bank;
    taskSetDate: string;
    commentary: string;
    completed: string;
}
