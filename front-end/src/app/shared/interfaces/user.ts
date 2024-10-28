import { Role } from "./role";

export interface RawUser {
    id: string;
    name: string;
    email: string;
    password: string;
    roleId: string;
}

export interface User {
    id: string;
    name: string;
    email: string;
    password: string;
    role: Role;
    config?: {
        volume: number;
    }
}
