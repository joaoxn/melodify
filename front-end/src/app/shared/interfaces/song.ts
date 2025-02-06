import { Genre } from "./genre";
import { Performer } from "./performer";

export interface RawSong {
    id: string;
    name: string;
    performerId?: string;
    genreIds: string[];
    thumbnailSrc?: string;
    views: number;
    src: string;
    fileType: string;
}

export interface Song {
    id: string;
    name: string;
    performer?: Performer;
    genres?: Genre[];
    thumbnailSrc?: string;
    views: number;
    src: string;
    fileType: string;
}
