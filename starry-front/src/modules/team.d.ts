export interface Team {
    id: number;
    name: string;
    description: string;
    userId: number;
    username: string;
    hasJoinNum: number;
    maxNum: number;
    expireTime: string;
    status: number;
    hasJoin: boolean;
}