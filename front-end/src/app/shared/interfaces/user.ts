import { LoopState } from "../enums/loop-state";
import { Role } from "./role";

export interface RawUser {
    id: string;
    name: string;
    email: string;
    password: string;
    roleId: string;
    config: {
        loopState: LoopState;
        volume: number;
    }
}

export interface User {
    id: string;
    name: string;
    email: string;
    password: string;
    role: Role;
    config: {
        loopState: LoopState;
        volume: number;
    }
}
