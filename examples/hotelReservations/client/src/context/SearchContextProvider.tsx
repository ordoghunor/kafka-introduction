import { createContext } from 'react';
import { ChildrenProps } from '../interface/childrenPropsInterface';

import {
  limitQuerryParamDefault, pageQuerryParamDefault,
  pageQuerryParamName, limitQuerryParamName,
} from '../config/application.json'
import { useSearchParamsState } from '../hooks/useSearchParamsState';

interface ContextData {
  limit: string;
  setLimit: (newState: string) => void;
  page: string;
  setPage: (newState: string) => void;
  
}

const initialContextData: ContextData = {
  limit: limitQuerryParamDefault,
  setLimit: () => { },
  page: pageQuerryParamDefault,
  setPage: () => { },
}


export const SearchContext = createContext<ContextData>(initialContextData);

export default function SearchContextProvider({ children }: ChildrenProps) {
  const [limit, setLimit] =
    useSearchParamsState(limitQuerryParamName, limitQuerryParamDefault);
  const [page, setPage] =
    useSearchParamsState(pageQuerryParamName, pageQuerryParamDefault);


  const value = {
    limit, setLimit,
    page, setPage,
  }

  return (
    <SearchContext.Provider value={value}>
      {children}
    </SearchContext.Provider>
  );
}
