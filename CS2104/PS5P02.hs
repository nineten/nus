-- U087139J
-- Ma Zhencai Jayden

count x l = foldr (\a b -> (1 + b)) 0 (filter (x==) l)