/**
 * 用户类别
 */
export type UserType = {
    id: number;
    username: string;
    gender: number;
    userAccount?: string;
    avatarUrl: string;
    profile: string;
    phone?: string;
    email?: string;
    tags?:  string;
    rating: number;
    updatedAt: Date;
    createTime: Date;
};
