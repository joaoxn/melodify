import { Song } from "./song";
import { User } from "./user";

export interface RawPlaylist {
    id: string;
    name: string;
    public: boolean;
    allowedUserIds: string[];
    views: number;
    songIds: string[];
}

export interface Playlist {
    id: string;
    name: string;
    public: boolean;
    allowedUsers: User[];
    views: number;
    songs: Song[];
}
