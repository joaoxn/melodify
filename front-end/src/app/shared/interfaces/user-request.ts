import { Role } from "./role";

export interface UserRequest {
    name: string;
    email: string;
    password: string;
    roleId: string;
}
