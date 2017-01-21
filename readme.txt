
1. Open the "Markov_chain_cluster" file.
2. select the dataset on which you intend to perfrom the clustering.
	i.e change the variable "file_no" in the main function, between 1, 2 and 3 to select the respective file.
3. Once the iterations have stopped, the resultant .clu file will be ready with the cluster_id of the respective nodes.
4. put the .clu file in the pajek application to visualise.

Reading the dataset : 
	If the path to the dataset needs to be changed, it can be done in the "ReadFile" class.

Writing the output : 
	The location for writing the output can be changed in the "write_to_file2" method of Markov chain cluster class.